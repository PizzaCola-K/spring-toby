package springboot.user.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import springboot.mail.DummyMailSender;
import springboot.user.dao.UserDao;
import springboot.user.domain.Level;
import springboot.user.domain.User;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static springboot.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static springboot.user.service.UserServiceImpl.MIN_RECCOMMNED_FOR_GOLD;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceImplTest {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserService userService;

    @Autowired
    UserLevelUpgradePolicy userLevelUpgradePolicy;

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;

    @Autowired
    DummyMailSender dummyMailSender;

    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "a@a.com"),
                new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "b@b.com"),
                new User("erwins", "신승환", "p3", Level.SILVER, 60, MIN_RECCOMMNED_FOR_GOLD - 1, "c@c.com"),
                new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMMNED_FOR_GOLD, "d@d.com"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "e@e.com")
        );
    }

    @Test
    public void upgradeLevels() {
        UserServiceImpl userService = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        MockMailSender mockMailSender = new MockMailSender();

        userService.setUserDao(mockUserDao);
        userService.setMailSender(mockMailSender);
        userService.setUserLevelUpgradePolicy(new DefaultUserLevelUpgradePolicy());

        userService.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();

        assertThat(updated).hasSize(2);

        checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
        checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertThat(request).hasSize(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    public void upgradeAllOrNothing() {
        UserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
        testUserServiceImpl.setUserDao(this.userDao);
        testUserServiceImpl.setUserLevelUpgradePolicy(this.userLevelUpgradePolicy);
        testUserServiceImpl.setMailSender(dummyMailSender);

        UserServiceTx tsUserService = new UserServiceTx();
        tsUserService.setUserService(testUserServiceImpl);
        tsUserService.setTransactionManager(transactionManager);

        for (User user : users) userDao.add(user);

        try {
            tsUserService.upgradeLevels();
            fail("TestUseServiceException expected");
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    public void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.setUserLevelUpgradePolicy(new DefaultUserLevelUpgradePolicy());

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArgumentCaptor
                = ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(mockMailSender, times(2)).send(mailMessageArgumentCaptor.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArgumentCaptor.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
    }
}
