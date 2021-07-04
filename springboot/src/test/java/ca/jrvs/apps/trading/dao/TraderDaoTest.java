package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    @Autowired
    private TraderDao traderDao;

    private Trader trader;

    @Before
    public void setUp() throws Exception {
        trader = new Trader();
        trader.setFirstName("Jeff");
        trader.setLastName("Test");
        trader.setCountry("CAN");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("jeff@example.com");
        traderDao.save(trader);
    }

    @After
    public void tearDown() throws Exception {
        traderDao.deleteById(trader.getId());
    }

    @Test
    public void getTableName() {
        assertEquals("trader", traderDao.getTableName());
    }

    @Test
    public void getIdColumnName() {
        assertEquals("id", traderDao.getIdColumnName());
    }

    @Test
    public void getEntityClass() {
        assertEquals(Trader.class, traderDao.getEntityClass());
    }

    @Test
    public void saveAll() {
        Trader trad = new Trader();
        trad.setFirstName("Dale");
        trad.setLastName("Morris");
        trad.setCountry("CAN");
        trad.setDob(new Date(System.currentTimeMillis()));
        trad.setEmail("jeff@example.com");

        List<Trader> traders = (List<Trader>) traderDao.saveAll(Arrays.asList(trad));
        logger.info(String.valueOf(trad.getId()));
        Trader rq1 = traderDao.findById(trad.getId()).get();

        assertEquals(2, traderDao.count());
        assertEquals(traders.get(0).getId(), rq1.getId());
    }

    @Test
    public void save() {
        Trader trad = new Trader();
        trad.setFirstName("Dale");
        trad.setLastName("Morris");
        trad.setCountry("CAN");
        trad.setDob(new Date(System.currentTimeMillis()));
        trad.setEmail("jeff@example.com");
        traderDao.save(trad);

        assertEquals("Dale", trad.getFirstName());
        assertEquals("Morris", trad.getLastName());
        assertEquals("CAN", trad.getCountry());
        assertEquals("jeff@example.com", trad.getEmail());
    }

    @Test
    public void findById() {
        Trader trad = traderDao.findById(trader.getId()).get();
        assertEquals(trader.getId(), trad.getId());
        assertEquals(trader.getFirstName(), trad.getFirstName());
        assertEquals(trader.getLastName(), trad.getLastName());
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(trader.getId()));
    }

    @Test
    public void findAll() {
        List<Trader> traders = traderDao.findAll();
        assertEquals(1, traderDao.count());
        assertEquals(trader.getId(), traders.get(0).getId());
    }

    @Test
    public void findAllById() {
        List<Trader> traders = traderDao.findAllById(Arrays.asList(trader.getId()));
        assertEquals(1, traders.size());
        assertEquals(trader.getId(), traders.get(0).getId());
    }

    @Test
    public void deleteById() {
        assertEquals(1, traderDao.count());
        traderDao.deleteById(trader.getId());
        assertEquals(0, traderDao.count());
    }

    @Test
    public void count() {
        assertEquals(1, traderDao.count());
    }

    @Test
    public void deleteAll() {
        traderDao.deleteAll();
        assertEquals(0, traderDao.count());
    }
}