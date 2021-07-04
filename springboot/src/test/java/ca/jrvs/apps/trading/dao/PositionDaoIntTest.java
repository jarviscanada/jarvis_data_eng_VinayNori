package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
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
public class PositionDaoIntTest {

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private SecurityOrderDao securityOrderDao;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private QuoteDao quoteDao;
    @Autowired
    private PositionDao positionDao;

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
        trader.setId(1);
        trader.setFirstName("Jeff");
        trader.setLastName("Test");
        trader.setCountry("CAN");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("jeff@example.com");
        traderDao.save(trader);

        account = new Account();
        account.setId(1);
        account.setTrader_id(trader.getId());
        account.setAmount(10d);
        accountDao.save(account);

        securityOrder = new SecurityOrder();
        securityOrder.setAccountId(account.getId());
        securityOrder.setStatus("FILLED");
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
    public void existsById() {
        assertTrue(positionDao.existsById(securityOrder.getAccountId()));
    }

    @Test
    public void findById() {
        Position position = positionDao.findById(securityOrder.getAccountId()).get();
        assertEquals(securityOrder.getTicker(), position.getTicker());
    }

    @Test
    public void findAll() {
        List<Position> positionList = positionDao.findAll();
        assertEquals(1, positionList.size());
        assertEquals(securityOrder.getTicker(), positionList.get(0).getTicker());
    }

    @Test
    public void findAllById() {
        List<Position> positionList = positionDao.findAllById(Arrays.asList(securityOrder.getAccountId()));
        assertEquals(1, positionList.size());
        assertEquals(securityOrder.getTicker(), positionList.get(0).getTicker());
    }

    @Test
    public void count() {
        assertEquals(1, positionDao.count());
    }
}