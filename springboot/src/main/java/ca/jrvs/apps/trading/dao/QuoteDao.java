package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updatedRowNo = updateOne(quote);
            logger.info(String.valueOf(updatedRowNo));
            if (updatedRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    private int updateOne(Quote quote) {
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?,"
                + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    /**
     * Helper method that makes sql update values objects
     *
     * @param quote
     * @return
     */
    private Object[] makeUpdateValues(Quote quote) {
        Object[] objects = new Object[6];
        objects[0] = quote.getLastPrice();
        objects[1] = quote.getBidPrice();
        objects[2] = quote.getBidSize();
        objects[3] = quote.getAskPrice();
        objects[4] = quote.getAskSize();
        objects[5] = quote.getTicker();
        return objects;
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> iterable) {
        List<S> lists = new ArrayList<>();
        iterable.forEach(s -> lists.add((S) save(s)));
        return lists;
    }

    /**
     * Find a quote by ticker
     *
     * @param ticker name
     * @return
     */
    @Override
    public Optional<Quote> findById(String ticker) {
        String sql_statement = "select * from " + TABLE_NAME + " where " + ID_COLUMN_NAME + "=?";
        Quote quote = new Quote();
        try {
            quote = jdbcTemplate.queryForObject(sql_statement,
                    BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find trader id: " + ticker, e);
        }
//    if (quote == null) {
//      throw new ResourceNotFoundException("Resource not found");
//    }
        return Optional.of(quote);
    }

    @Override
    public boolean existsById(String ticker) {
        int rowCount = 0;
        String sql_statement = "select count(*) from " + TABLE_NAME + " where " + ID_COLUMN_NAME + "=?";
        try {
            rowCount = jdbcTemplate.queryForObject(sql_statement, Integer.class, ticker);
        } catch (NullPointerException e) {
            throw new NullPointerException("Unable to find ticker");
        }
        return rowCount == 1;
    }

    @Override
    public List<Quote> findAll() {
        String sql_statement = "select * from " + TABLE_NAME;
        return jdbcTemplate.query(sql_statement, BeanPropertyRowMapper.newInstance(Quote.class));
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from " + TABLE_NAME, Long.class);
    }

    @Override
    public void deleteById(String ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        jdbcTemplate.update(deleteSql, ticker);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM " + TABLE_NAME);
    }
}