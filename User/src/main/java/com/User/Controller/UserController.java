package com.User.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.User.Entity.User;
import com.User.Service.UserService;
import com.User.binding.UserRequest;
import com.User.binding.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /users
    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setAge(req.getAge());
        User saved = userService.addUser(user);

        UserResponse resp = new UserResponse();
        resp.setId(saved.getId());
        resp.setName(saved.getName());
        resp.setAge(saved.getAge());

        return ResponseEntity.ok(resp);
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> viewUser(@PathVariable Integer id) {
        User user = userService.viewUser(id);
        return ResponseEntity.ok(user);
    }
}
