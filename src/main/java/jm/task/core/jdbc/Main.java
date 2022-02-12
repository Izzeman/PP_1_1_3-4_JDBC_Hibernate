package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Anton", "Ivanov", (byte) 35);
        userService.saveUser("Anna", "Ivanova", (byte) 32);
        userService.saveUser("Oleg", "Ivanov", (byte) 10);
        userService.saveUser("Daria", "Ivanova", (byte) 7);
        List<User> users = userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
