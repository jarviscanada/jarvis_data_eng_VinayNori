package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract  public String getIdColumnName();

    abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     * @param entity to be saved
     * @return save entity
     */
    public <S extends T> S save(S entity){
        if(existsById(entity.getId())){
            if(updateOne(entity) != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(entity);
        }
        return entity;
    }

    /**
     * helper method that saves one quote
     */
    private <S extends T> void addOne(S entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
    }

    /**
     * helper method that updates one quote
     */
    abstract public int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id){
        Optional<T> entity = Optional.empty();
        String sqlQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
        try{
            entity = Optional.ofNullable((T) getJdbcTemplate()
                    .queryForObject(sqlQuery,
                            BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch (IncorrectResultSizeDataAccessException e){
            logger.debug("Can't find trader id:" +id,e);
        }
        return entity;
    }

    @Override
    public boolean existsById(Integer id){
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE "+ getIdColumnName() + " =?";
        return findById(id).isPresent();
    }

    @Override
    public List<T> findAll(){
        String sqlQuery = "SELECT * FROM " + getTableName();
        return getJdbcTemplate().query(sqlQuery,
                BeanPropertyRowMapper.newInstance(getEntityClass()));
    }

    @Override
    public  List<T> findAllById(Iterable<Integer> ids){
        List<T> records = new ArrayList<>();
        for(int id:ids){
            records.add(findById(id).get());
        }
        return records;
    }

    @Override
    public void deleteById(Integer id){
        String sqlQuery = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
        getJdbcTemplate().update(sqlQuery, id);
    }

    @Override
    public long count(){
        String sqlQuery = "SELECT COUNT(*) FROM " + getTableName();
        return Optional.ofNullable(getJdbcTemplate().queryForObject(sqlQuery,long.class)).get();
    }

    @Override
    public void deleteAll(){
        String sqlQuery = "DELETE FROM " + getTableName();
        getJdbcTemplate().update(sqlQuery);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        List<S> sList = new ArrayList<>();
        iterable.forEach(item -> sList.add(save(item)));
        return sList;
    }

    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}