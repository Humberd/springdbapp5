package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Autowired
    HashService hashService;
    @Autowired
    BasicUserRepository basicUserRepository;

    @Override
    public void addUser(User user) throws IllegalUsernameException, IncorrectEmailException {
        if (user.getUsername().equals("admin")) {
            throw new IllegalUsernameException();
        }

        if (!isEmail(user.getEmail())) {
            throw new IncorrectEmailException();
        }

        try {
            user.setPassword(hashService.getHash(user.getPassword()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        basicUserRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return basicUserRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return basicUserRepository.findAllUsers();
    }

    @Override
    public User removeUser(String username) {
        return basicUserRepository.remove(username);
    }

    @Override
    public boolean verifyUser(String username, String password) {
        User user = basicUserRepository.findByUsername(username);
        try {
            return hashService.getHash(password).equals(user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}
