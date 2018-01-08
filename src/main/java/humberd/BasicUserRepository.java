package humberd;

public interface BasicUserRepository {
    User findByUsername(String username);
    void save(User user);
    void remove(String username);
}
