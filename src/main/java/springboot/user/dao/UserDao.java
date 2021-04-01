package springboot.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import springboot.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("select * from users where id = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                }
                if (user == null) {
                    throw new EmptyResultDataAccessException(1);
                }
                return user;
            }
        }
    }

    public void deleteAll() {
        //jdbcTemplate.update(c -> c.prepareStatement("delete from users"));
        jdbcTemplate.update("delete from users");
    }

    public Integer getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        /*
        return jdbcTemplate.query(
                c -> c.prepareStatement("select count(id) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                });*/
    }
}
