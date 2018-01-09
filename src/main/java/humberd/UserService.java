package humberd;

import java.util.List;

public interface UserService {
    class IllegalUsernameException extends RuntimeException {}
    class IncorrectEmailException extends RuntimeException {}
    void addUser(User user) throws IllegalUsernameException, IncorrectEmailException ;
    User getUser(String username);
    List<User> getAllUsers();
    Long removeUser(String username);
    boolean verifyUser(String username, String password);
}