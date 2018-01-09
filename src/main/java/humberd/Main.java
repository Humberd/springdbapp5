package humberd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;

@Component
public class Main {
    @Autowired
    UserService userService;

    private Scanner stdin = new Scanner(System.in);

    public static final String ADD_USER = "1";
    public static final String VERIFY_PASSWORD = "2";
    public static final String FIND_USER = "3";
    public static final String FIND_ALL_USERS = "4";
    public static final String REMOVE_USER = "5";


    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        System.out.println("____HELLO IN SPRING APP____");
        while (true) {
            System.out.println();
            System.out.println("What do you want to do?");
            System.out.println("[" + ADD_USER + "] Add new User");
            System.out.println("[" + VERIFY_PASSWORD + "] Verify Password");
            System.out.println("[" + FIND_USER + "] Find User");
            System.out.println("[" + FIND_ALL_USERS + "] Find All Users");
            System.out.println("[" + REMOVE_USER + "] Remove User");
            switch (getFromStdin("")) {
                case ADD_USER:
                    addUserFromStdin();
                    break;
                case VERIFY_PASSWORD:
                    verifyPasswordFromStdin();
                    break;
                case FIND_USER:
                    findUserFromStdin();
                    break;
                case FIND_ALL_USERS:
                    findAllUsersFromStdin();
                    break;
                case REMOVE_USER:
                    removeUserFromStdin();
                    break;
                default:
                    System.err.println("Invalid option");
                    continue;
            }
        }
    }

    private void addUserFromStdin() {
        System.out.println();
        System.out.println("__Add new User__");
        String username = getFromStdin("Username");
        String password = getFromStdin("Password");
        String name = getFromStdin("Name");
        String email = getFromStdin("Email");
        User user = new User(username, password, name, email);

        try {
            userService.addUser(user);
            System.out.println("--User was added--");
        } catch (UserService.IllegalUsernameException e) {
            System.err.println("--Invalid username--");
        } catch (UserService.IncorrectEmailException e) {
            System.err.println("--Incorrect email--");
        }
    }

    private void verifyPasswordFromStdin() {
        System.out.println();
        System.out.println("__Verify Password__");
        String username = getFromStdin("Username");
        String password = getFromStdin("Password");

        if (userService.verifyUser(username, password)) {
            System.out.println("--Password Verified!--");
        } else {
            System.err.println("--Password not verified--");
        }
    }

    private void findUserFromStdin() {
        System.out.println();
        System.out.println("__Find User__");
        String username = getFromStdin("Username");

        User user = userService.getUser(username);
        System.out.println();
        if (user == null) {
            System.err.println("--There is no user with username: " + username + "--");
        } else {
            System.out.println("User found.");
            System.out.println(user);
        }
    }

    private void findAllUsersFromStdin() {
        System.out.println();
        System.out.println("__Find All Users__");

        List<User> allUsers = userService.getAllUsers();
        System.out.println();
        System.out.println("Found " + allUsers.size() + " users");
        for (User user : allUsers) {
            System.out.println(user);
        }
    }

    private void removeUserFromStdin() {
        System.out.println();
        System.out.println("__Remove User__");
        String username = getFromStdin("Username");

        Long removedUser = userService.removeUser(username);
        System.out.println();
        if (removedUser == null) {
            System.err.println("--User does not exist--");
        } else {
            System.out.println("Successfully removed " + username + " from database");
        }
    }

    private String getFromStdin(String label) {
        System.out.print(label + ": ");
        return stdin.nextLine().trim();
    }
}
