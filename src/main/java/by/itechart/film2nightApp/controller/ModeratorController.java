package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/moderator/")
public class ModeratorController {

    private final UserService userService;

    @Autowired
    public ModeratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name="id") Integer id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping("/blocked/users/{id}")
    public ResponseEntity<User> blockedUserById(@PathVariable(name="id") Integer id){
        User user = userService.findById(id);
        user.setIs_blocked("true");
        return ResponseEntity.ok().body(user);
    }


}
