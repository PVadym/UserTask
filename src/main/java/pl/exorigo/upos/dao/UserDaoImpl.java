package pl.exorigo.upos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.exorigo.upos.entity.User;
import pl.exorigo.upos.enums.Gender;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Вадим on 20.09.2017.
 */
@Component
public class UserDaoImpl implements UserDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {

        String sql = "insert into users (name, surname, password, gender, email) values (?,?,?,?,?)";
        this.jdbcTemplate.update(sql, user.getName(),user.getSurname(), user.getPassword(), user.getGender().name(), user.getEmail());
    }

    @Override
    public void delete(long id) {
        String sql = "delete from users where id = ?";
        this.jdbcTemplate.update(sql,id);

    }

    @Override
    public void delete(User user) {
        delete(user.getId());

    }

    @Override
    public void update(User user) {
        String sql = "update users set name = ?, surname = ?, password = ?, gender = ?, email = ? where id = ?";
        this.jdbcTemplate.update(sql,user.getName(),user.getSurname(), user.getPassword(), user.getGender().name(), user.getEmail(), user.getId());


    }

    @Override
    public User getById(long id) {
        String sql = "select * from users where id = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
    }

    @Override
    public User getByName(String name) {
        String sql = "select * from users where name like ?";
        try {
            return this.jdbcTemplate.queryForObject(sql,new Object[]{name},new UserMapper());

        } catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        return this.jdbcTemplate.query(sql,new Object[]{},new UserMapper());
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPassword(resultSet.getString("password"));
            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setEmail(resultSet.getString("email"));
            return user;
        }
    }
}
