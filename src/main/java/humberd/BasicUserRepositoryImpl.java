package humberd;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BasicUserRepositoryImpl implements BasicUserRepository {
    private Map<String, User> repo = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return repo.get(username);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public void save(User user) {
        repo.put(user.getUsername(), user);
    }

    @Override
    public User remove(String username) {
        return repo.remove(username);
    }
}
