package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
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
        return ID_COLUMN;
    }

    @Override
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    /**
     * helper method that updates one quote
     *
     * @param securityOrder
     */
    @Override
    public int updateOne(SecurityOrder securityOrder) {
        String update_sql = "UPDATE " + TABLE_NAME + " SET account_id=?, status=?, ticker=?, size=?,"
                + "price=?, notes=? WHERE " + ID_COLUMN + "=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(securityOrder));
    }

    /**
     * Helper method that makes sql update values objects
     *
     * @param securityOrder
     * @return
     */
    private Object[] makeUpdateValues(SecurityOrder securityOrder) {
        Object[] columns = {
                securityOrder.getAccountId(),
                securityOrder.getStatus(),
                securityOrder.getTicker(),
                securityOrder.getSize(),
                securityOrder.getPrice(),
                securityOrder.getNotes(),
                securityOrder.getId()
        };
        return columns;
    }

    public void deleteByAccountId(int id){
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE account_id=?" ;
        jdbcTemplate.update(sqlQuery, id);
    }
}