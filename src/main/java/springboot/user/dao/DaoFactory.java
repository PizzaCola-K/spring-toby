package springboot.user.dao;

import java.sql.SQLException;

public class DaoFactory {
    public UserDao userDao() throws SQLException {
        ConnectionMaker connectionMaker = new H2ConnectionMaker();
        return new UserDao(connectionMaker);
    }
}
