package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
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
public class SecurityOrderDaoIntTest {

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private SecurityOrderDao securityOrderDao;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;
    private Account account;
    private Trader trader;
    private SecurityOrder securityOrder;

    @Before
    public void setUp() throws Exception {
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        trader = new Trader();
        trader.setFirstName("Jeff");
        trader.setLastName("Test");
        trader.setCountry("CAN");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("jeff@example.com");
        traderDao.save(trader);

        account = new Account();
        account.setTrader_id(trader.getId());
        account.setAmount(10d);
        accountDao.save(account);

        securityOrder = new SecurityOrder();
        securityOrder.setAccountId(account.getId());
        securityOrder.setStatus("Active");
        securityOrder.setTicker(savedQuote.getTicker());
        securityOrder.setSize(36);
        securityOrder.setPrice(10d);
        securityOrder.setNotes("These are my notes.");
        securityOrderDao.save(securityOrder);
    }

    @After
    public void tearDown() throws Exception {
        securityOrderDao.deleteAll();
        quoteDao.deleteById(savedQuote.getTicker());
        accountDao.deleteById(account.getId());
        traderDao.deleteById(trader.getId());
    }

    @Test
    public void getTableName() {
        assertEquals("security_order", securityOrderDao.getTableName());
    }

    @Test
    public void getIdColumnName() {
        assertEquals("id", securityOrderDao.getIdColumnName());
    }

    @Test
    public void getEntityClass() {
        assertEquals(SecurityOrder.class, securityOrderDao.getEntityClass());
    }

    @Test
    public void saveAll() {
        SecurityOrder securityOrder1 = new SecurityOrder();
        securityOrder1.setAccountId(account.getId());
        securityOrder1.setStatus("Active");
        securityOrder1.setTicker(savedQuote.getTicker());
        securityOrder1.setSize(36);
        securityOrder1.setPrice(10d);
        securityOrder1.setNotes("Notes");

        List<SecurityOrder> sOrders = (List<SecurityOrder>) securityOrderDao.saveAll(Arrays.asList(securityOrder1));
        logger.info(String.valueOf(securityOrder1.getId()));
        SecurityOrder rq1 = securityOrderDao.findById(securityOrder1.getId()).get();

        assertEquals(2, securityOrderDao.count());
        assertEquals(sOrders.get(0).getId(), rq1.getId());
    }

    @Test
    public void save() {
        assertEquals(new Integer(1), securityOrder.getId());
        assertEquals(account.getId(),securityOrder.getAccountId());
        assertEquals("Active", securityOrder.getStatus());
        assertEquals("AAPL", securityOrder.getTicker());
        assertEquals(new Integer(36), securityOrder.getSize());
        assertEquals(new Double(10), securityOrder.getPrice());
        assertEquals("These are my notes.", securityOrder.getNotes());
    }

    @Test
    public void findById() {
        SecurityOrder sO = securityOrderDao.findById(securityOrder.getId()).get();
        assertEquals(account.getId(),sO.getAccountId());
        assertEquals("Active", sO.getStatus());
        assertEquals("AAPL", sO.getTicker());
        assertEquals(new Integer(36), sO.getSize());
        assertEquals(new Double(10), sO.getPrice());
        assertEquals("These are my notes.", sO.getNotes());
    }

    @Test
    public void existsById() {
        assertTrue(securityOrderDao.existsById(securityOrder.getId()));
    }

    @Test
    public void findAll() {
        List<SecurityOrder> securityOrders = securityOrderDao.findAll();
        assertEquals(1, securityOrderDao.count());
        assertEquals(securityOrder.getId(), securityOrders.get(0).getId());
    }

    @Test
    public void findAllById() {
        List<SecurityOrder> securityOrders = securityOrderDao.findAllById(Arrays.asList(securityOrder.getId()));
        assertEquals(1, securityOrders.size());
        assertEquals(securityOrder.getId(), securityOrders.get(0).getId());
    }

    @Test
    public void deleteById() {
        assertEquals(1, securityOrderDao.count());
        securityOrderDao.deleteById(securityOrder.getId());
        assertEquals(0, securityOrderDao.count());
    }

    @Test
    public void count() {
        assertEquals(1, securityOrderDao.count());
    }

    @Test
    public void deleteAll() {
        securityOrderDao.deleteAll();
        assertEquals(0, securityOrderDao.count());
    }
}