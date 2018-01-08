package humberd;

import java.util.List;

public interface BasicUserRepository {
    User findByUsername(String username);
    List<User> findAllUsers();
    void save(User user);
    User remove(String username);
}
