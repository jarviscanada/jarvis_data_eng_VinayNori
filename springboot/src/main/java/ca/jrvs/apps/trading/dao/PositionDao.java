package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PositionDao {

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "account_id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    public Optional<Position> findById(int id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +ID_COLUMN + "=?";
        try {
            Position position = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Position.class), id);
            return Optional.ofNullable(position);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public boolean existsById(Integer id){
        return findById(id).isPresent();
    }

    public List<Position> findAll(){
        String sqlQuery = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sqlQuery, BeanPropertyRowMapper.newInstance(Position.class));
    }

    public long count(){
        String sqlQuery = "SELECT COUNT(*) FROM " +TABLE_NAME;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery,long.class)).get();
    }

    public List<Position> findAllById(Iterable<Integer> ids){
        List<Position> positions = new ArrayList<>();
        ids.forEach(id -> positions.add(findById(id).get()));
        return positions;
    }

}