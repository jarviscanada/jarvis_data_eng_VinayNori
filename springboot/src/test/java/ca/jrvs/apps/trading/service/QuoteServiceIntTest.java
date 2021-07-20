package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void setUp() throws Exception {
        quoteDao.deleteAll();
    }

    @Test
    public void findIexQuoteByTicker() {
        Quote q2 = new Quote();
        q2.setAskPrice(5d);
        q2.setAskSize(10);
        q2.setBidPrice(5d);
        q2.setBidSize(10);
        q2.setTicker("MSFT");
        q2.setLastPrice(5d);

        IexQuote iexQuote = quoteService.findIexQuoteByTicker(q2.getTicker());
        assertEquals(q2.getTicker(), iexQuote.getSymbol());
    }

    @Test
    public void updateMarketData() {
        Quote q2 = new Quote();
        q2.setAskPrice(5d);
        q2.setAskSize(20);
        q2.setBidPrice(5d);
        q2.setBidSize(20);
        q2.setTicker("MSFT");
        q2.setLastPrice(5d);
        quoteDao.save(q2);
        quoteService.updateMarketData();

        Quote q3 = quoteDao.findById(q2.getTicker()).get(); // after update
        assertEquals(q2.getTicker(), q3.getTicker());
    }

    @Test
    public void saveQuotes() {
        List<String> qs = Arrays.asList("AAPL", "FB", "MSFT");
        List<Quote> quotes = quoteService.saveQuotes(qs);
        assertEquals(3, quotes.size());
        assertEquals(qs.size(), quotes.size());
    }

    @Test
    public void saveQuote() {
        Quote quote = quoteService.saveQuote("AAPL");
        assertEquals("AAPL", quote.getTicker());
    }
}