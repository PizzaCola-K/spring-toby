package springboot.user.service;

import springboot.user.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
