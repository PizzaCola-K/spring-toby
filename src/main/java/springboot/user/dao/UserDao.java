package springboot.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import springboot.user.domain.User;

import javax.sql.DataSource;

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

    public User get(String id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[]{id},
                new int[]{1},
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                });
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
