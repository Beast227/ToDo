package org.project.todo.user;

import jakarta.validation.Valid;
import org.project.todo.user.dto.UserLogin;
import org.project.todo.user.dto.UserRequest;
import org.project.todo.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        String token = userService.login(userLogin);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/data")
    public UserResponse getUser() {
        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUser(id);
    }

    @PutMapping("/update")
    public UserResponse updateUser(@RequestBody @Valid UserRequest userRequest) {
        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/delete")
    public void deleteUser() {
        userService.deleteUser((UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
