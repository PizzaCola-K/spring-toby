package springboot.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt, String... args) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = stmt.makePreparedStatement(c)) {
            int index = 1;
            for (String arg : args) {
                ps.setString(index, arg);
                index++;
            }
            ps.executeUpdate();
        }
    }

    public void executeSql(String query, String... args) throws SQLException {
        workWithStatementStrategy(c -> c.prepareStatement(query), args);
    }

}
