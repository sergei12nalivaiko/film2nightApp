package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().body("User with id = " + id + " was deleted");
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/user/block/{id}")
    public ResponseEntity<User> blockUserById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().body(userService.blockUserById(id));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/users/active/{year}")
    public ResponseEntity getActiveUserForYear(@PathVariable(name = "year") Integer year) {
        return ResponseEntity.ok().body(userService.getActiveUsersForYear(year));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/users/active/{year}/{month}")
    public ResponseEntity getActiveUserForMonth(@PathVariable(name = "year") Integer year, @PathVariable(name = "month") Integer month) {
        return ResponseEntity.ok().body(userService.getActiveUsersForMonth(year, month));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/users/active/{year}/{month}/{week}")
    public ResponseEntity getActiveUserForWeek(@PathVariable(name = "year") Integer year, @PathVariable(name = "month") Integer month, @PathVariable(name = "week") Integer week) {
        return ResponseEntity.ok().body(userService.getActiveUsersForWeek(year, month, week));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/users/active/day/{year}/{month}/{day}")
    public ResponseEntity getActiveUserForDay(@PathVariable(name = "year") Integer year, @PathVariable(name = "month") Integer month, @PathVariable(name = "day") Integer day) {
        return ResponseEntity.ok().body(userService.getActiveUsersForDay(year, month, day));
    }
}
