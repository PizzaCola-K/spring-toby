package springboot.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2ConnectionMaker implements ConnectionMaker {

    static {
        try {
            Connection c = new H2ConnectionMaker().makeConnection();
            PreparedStatement ps = c.prepareStatement(
                    "create table users (" +
                            "id varchar(10) primary key, " +
                            "name varchar(20) not null, " +
                            "password varchar(10) not null, " +
                            "email varchar(255) not null, " +
                            "level int not null," +
                            "login int not null," +
                            "recommend int not null)");
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Connection makeConnection() throws SQLException {
        String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String DB_USER_NAME = "sa";
        String DB_PASSWORD = "";
        return DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
    }
}
