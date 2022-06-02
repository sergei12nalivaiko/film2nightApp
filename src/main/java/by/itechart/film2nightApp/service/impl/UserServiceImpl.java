package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.entity.Role;
import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.repository.RoleRepository;
import by.itechart.film2nightApp.repository.UserRepository;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Add role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User findByUsername(String username) {
        log.info("Get user {} ", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User blockUserById(long id) {
        User user = userRepository.findById(id).get();
        user.setIsBlocked(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    @Override
    public Map<Object, Object> getActiveUsersForYear(int year) {

        Map<Object, Object> countOfActiveUsers = new TreeMap<>();
        LocalDateTime dateBegin = Year.of(year).atMonth(1).atDay(1).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(12).atEndOfMonth().atTime(23, 59, 59);
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<User> list = userRepository.findActiveUsers(timestampBegin, timestampEnd);
        countOfActiveUsers.put(year, list.size());
        return countOfActiveUsers;
    }

    @Override
    public Map<Object, Object> getActiveUsersForMonth(int year, int month) {
        Map<Object, Object> countOfUsersForMonth = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atEndOfMonth().atTime(23, 59, 59);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<User> list = userRepository.findActiveUsers(timestampBegin, timestampEnd);
        countOfUsersForMonth.put(monthReport, list.size());
        return countOfUsersForMonth;
    }

    @Override
    public Map<Object, Object> getActiveUsersForWeek(int year, int month, int week) {
        Map<Object, Object> countOfUsersForWeek = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0).plusWeeks(week - 1);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0).plusWeeks(week);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<User> list = userRepository.findActiveUsers(timestampBegin, timestampEnd);
        countOfUsersForWeek.put(dateBegin + "  -  " + dateEnd, list.size());
        return countOfUsersForWeek;
    }

    @Override
    public Map<Object, Object> getActiveUsersForDay(int year, int month, int day) {
        Map<Object, Object> countOfUsersForDay = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(day).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atDay(day).atTime(23, 59, 59);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<User> list = userRepository.findActiveUsers(timestampBegin, timestampEnd);
        countOfUsersForDay.put(dateBegin + "  -  " + dateEnd, list.size());
        return countOfUsersForDay;
    }
}
