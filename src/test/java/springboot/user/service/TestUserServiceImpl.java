package springboot.user.service;

import springboot.user.domain.User;

public class TestUserServiceImpl extends UserServiceImpl {
    private final String id;

    public TestUserServiceImpl(String id) {
        this.id = id;
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }
}
