package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    @Override
    public <S extends IexQuote> S save(S entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));
        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        logger.info(tickers.toString());
        tickers.forEach(t -> {
            if (t.length() < 2 || t.length() > 5 || t.matches(".*[0-9]+.*")) {
                throw new IllegalArgumentException("Ticker is not of proper format");
            }
        });

        String ids = StreamSupport.stream(tickers.spliterator(), false)
                .collect(Collectors.joining(","));

        String uri = String.format(IEX_BATCH_URL, ids);
        logger.debug(uri);

        Optional<String> json = executeHttpGet(uri);
        JSONObject jo = new JSONObject(json.get());

        if (jo.length() == 0) {
            throw new IllegalArgumentException("Invalid ticker");
        }

        Iterator<String> keys = jo.keys();

        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectReader reader = m.reader().withRootName("quote");

        List<IexQuote> quotes = new ArrayList<>();
        do {
            String key = jo.get(keys.next()).toString();
            try {
                IexQuote quote = reader.forType(IexQuote.class).readValue(key);
                quotes.add(quote);
            } catch (IOException e) {
                throw new DataRetrievalFailureException("Couldn't convert JSON to object");
            }
        } while (keys.hasNext());

        return quotes;
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }


    private Optional<String> executeHttpGet(String url) {
        HttpClient httpClient = getHttpClient();
        URI uri = URI.create(url);
        logger.debug(url);

        try {
            HttpResponse response = httpClient.execute(new HttpGet(uri));

            int code = response.getStatusLine().getStatusCode();

            if (code != 200) {
                throw new RuntimeException("Status code invalid: " + code);
            }

            String data = EntityUtils.toString(response.getEntity());

            return Optional.of(data);
        } catch (IOException e) {
            throw new DataRetrievalFailureException("Unable to fetch the data.");
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                // prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }
}