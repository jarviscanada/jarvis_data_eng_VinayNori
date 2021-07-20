package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

    private static final String TABLE_NAME = "account";
    private static final String ID_COLUMN_NAME = "id";
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN_NAME;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    /**
     * helper method that updates one quote
     *
     * @param account
     */
    @Override
    public int updateOne(Account account) {
        String sqlQuery="UPDATE " + getTableName()
                + " SET trader_id=?, amount=? WHERE " + getIdColumnName() + "=?";
        return jdbcTemplate.update(sqlQuery,account.getTrader_id(), account.getAmount(),account.getId());
    }

    public Optional<Account> findByTraderId(Integer traderId) {
        Account account = new Account();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE trader_id=?";

        try{
            account = jdbcTemplate.queryForObject(sqlQuery,
                    BeanPropertyRowMapper.newInstance(Account.class),traderId);
        } catch (IncorrectResultSizeDataAccessException e){
            logger.debug("Can't fing trader id:" +traderId, e);
        }
        return Optional.of(account);
    }
}