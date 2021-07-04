package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
    }

    /**
     * Update quote table against IEX source - get all quotes from the db - foreach ticker get
     * iexQuote - convert iexQuote to quote entity - persist quote to db
     *
     * @throws ca.jrvs.apps.trading.dao                    if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public void updateMarketData() {
        List<Quote> quotes = quoteDao.findAll();
        List<String> ids = new ArrayList<>();
        quotes.forEach(q -> {
            ids.add(q.getTicker());
        });
        List<IexQuote> allIds = marketDataDao.findAllById(ids);
        Iterable<Quote> quoteIterable = allIds.stream().map(QuoteService::buildQuoteFromIexQuote).collect(Collectors.toList());
        quoteDao.saveAll(quoteIterable);
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity Note: `iexQuote.getLatestprice() == null` if
     * the stock market is closed. Make sure set a default value for number field(s).
     *
     * @param iexQuote
     * @return
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setAskSize(iexQuote.getIexAskSize());
        quote.setLastPrice(iexQuote.getLatestPrice());
        return quote;
    }

    /**
     * Validate (against IEX) and save given tickers to quote table.
     * <p>
     * - Get iexQuote(s) - convert each iexQuote to Quote entity - persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @return
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<IexQuote> iQuotes = marketDataDao.findAllById(tickers);
        Iterable<Quote> q = iQuotes.stream().map(QuoteService::buildQuoteFromIexQuote)
                .collect(Collectors.toList());
        return quoteDao.saveAll(q);
    }

    /**
     * Helper method
     *
     * @param ticker
     * @return
     */
    public Quote saveQuote(String ticker) {
        IexQuote iexQuote = marketDataDao.findById(ticker).get();
        return quoteDao.save(buildQuoteFromIexQuote(iexQuote));
    }

    /**
     * Update a given quote to quote table without validation
     *
     * @param quote entity
     * @return
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}