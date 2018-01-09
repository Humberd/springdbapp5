package humberd;

import humberd.repositories.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceDBImpl implements UserService{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private HashService hashService;
    private UserRepository basicUserRepository;

    public UserServiceDBImpl(HashService hashService,
                           UserRepository basicUserRepository) {
        this.hashService = hashService;
        this.basicUserRepository = basicUserRepository;
    }

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
        return basicUserRepository.findAll();
    }

    @Override
    public Long removeUser(String username) {
        return basicUserRepository.deleteByUsername(username);
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
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
