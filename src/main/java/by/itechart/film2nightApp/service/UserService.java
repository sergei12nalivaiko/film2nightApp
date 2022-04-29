package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.entity.Role;
import by.itechart.film2nightApp.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User findByUsername(String username);
    List<User> getUsers();
    User findById(int id);
}
