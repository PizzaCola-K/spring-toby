package springboot.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springboot.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[]{id},
                new int[]{1},
                userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("select count(id) from users", Integer.class);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }
}
