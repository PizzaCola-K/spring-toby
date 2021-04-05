package springboot.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String DB_USER_NAME = "sa";
        String DB_PASSWORD = "";
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USER_NAME);
        dataSource.setPassword(DB_PASSWORD);

        return dataSource;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new H2ConnectionMaker();
    }
}
