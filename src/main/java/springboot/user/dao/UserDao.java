package springboot.user.dao;

import springboot.user.domain.User;
import java.sql.*;

public class UserDao {
    private final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private final String DB_USER_NAME = "sa";
    private final String DB_PASSWORD = "";
    public UserDao() throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        PreparedStatement ps = c.prepareStatement(
                "create table users (" +
                        "id varchar(10) primary key, " +
                        "name varchar(20) not null, " +
                        "password varchar(10) not null)");
        ps.execute();
        ps.close();
        c.close();
    }

    public void add(User user) throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
