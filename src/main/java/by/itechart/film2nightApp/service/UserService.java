package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.entity.Role;
import by.itechart.film2nightApp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User findByUsername(String username);

    List<User> getUsers();

    User findById(long id);

    void deleteUserById(long id);

    User blockUserById(long id);

    User getUserIdFromSecurityContext();

    Map<Object, Object> getActiveUsersForYear(int year);

    Map<Object, Object> getActiveUsersForMonth(int year, int month);

    Map<Object, Object> getActiveUsersForWeek(int year, int month, int week);

    Map<Object, Object> getActiveUsersForDay(int year, int month, int day);
}
