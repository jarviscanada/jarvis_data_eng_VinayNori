package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void insertOne() throws Exception {
        quoteDao.deleteAll();
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(25);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
    }

    @Test
    public void findById() {
        Optional<Quote> quote = quoteDao.findById("AAPL");
        assertEquals("AAPL", quote.get().getTicker());
        assertEquals(new Double(10.1), quote.get().getLastPrice());
    }

    @Test
    public void existsById() {
        assertTrue(quoteDao.existsById("AAPL"));
        assertFalse(quoteDao.existsById("FB"));
    }

    @Test
    public void findAll() {
        List<Quote> quotes = quoteDao.findAll();
        assertEquals(1, quoteDao.count());
        assertEquals(savedQuote.getTicker(), quotes.get(0).getTicker());
    }

    @Test
    public void saveAll() {
        Quote q1 = new Quote();
        q1.setAskPrice(5d);
        q1.setAskSize(10);
        q1.setBidPrice(5d);
        q1.setBidSize(10);
        q1.setTicker("FB");
        q1.setLastPrice(5d);

        Quote q2 = new Quote();
        q2.setAskPrice(5d);
        q2.setAskSize(20);
        q2.setBidPrice(5d);
        q2.setBidSize(20);
        q2.setTicker("MSFT");
        q2.setLastPrice(5d);

        List<Quote> quotes = quoteDao.saveAll(Arrays.asList(q1, q2));
        Quote rq1 = quoteDao.findById("FB").get();
        Quote rq2 = quoteDao.findById("MSFT").get();

        assertEquals(quotes.get(0).getTicker(), rq1.getTicker());
        assertEquals(quotes.get(1).getTicker(), rq2.getTicker());
    }

    @Test
    public void save() {
        Quote q = new Quote();
        q.setAskPrice(5d);
        q.setAskSize(10);
        q.setBidPrice(5d);
        q.setBidSize(10);
        q.setTicker("FB");
        q.setLastPrice(5d);
        Quote testQ = quoteDao.save(q);

        assertEquals(testQ.getTicker(), q.getTicker());
        quoteDao.deleteAll();
    }

    @Test
    public void count() {
        assertEquals(1, quoteDao.count());
    }

    @Test
    public void deleteById() {
        quoteDao.deleteById(savedQuote.getTicker());
        assertEquals(0, quoteDao.count());
    }

    @Test
    public void deleteAll() {
        Quote q1 = new Quote();
        q1.setAskPrice(5d);
        q1.setAskSize(10);
        q1.setBidPrice(5d);
        q1.setBidSize(10);
        q1.setTicker("FB");
        q1.setLastPrice(5d);

        Quote q2 = new Quote();
        q2.setAskPrice(5d);
        q2.setAskSize(10);
        q2.setBidPrice(5d);
        q2.setBidSize(10);
        q2.setTicker("MSFT");
        q2.setLastPrice(5d);

        quoteDao.saveAll(Arrays.asList(q1, q2));
        assertEquals(3, quoteDao.count());
        quoteDao.deleteAll();
        assertEquals(0, quoteDao.count());
    }

    @After
    public void deleteOne() throws Exception {
        quoteDao.deleteAll();
//    quoteDao.deleteById(savedQuote.getTicker());
    }
}