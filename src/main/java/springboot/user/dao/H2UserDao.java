package springboot.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2UserDao extends UserDao {
    public H2UserDao() throws SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
                "create table users (" +
                        "id varchar(10) primary key, " +
                        "name varchar(20) not null, " +
                        "password varchar(10) not null)");
        ps.execute();
        ps.close();
        c.close();
    }

    public Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String DB_USER_NAME = "sa";
        String DB_PASSWORD = "";
        return DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
    }

}
