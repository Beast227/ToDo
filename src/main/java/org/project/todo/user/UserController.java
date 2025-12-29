package org.project.todo.user;

import org.project.todo.user.dto.UserLogin;
import org.project.todo.user.dto.UserRequest;
import org.project.todo.user.dto.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLogin userLogin) {
        return userService.login(userLogin);
    }

}
