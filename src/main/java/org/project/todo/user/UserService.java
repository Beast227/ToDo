package org.project.todo.user;

import jakarta.persistence.EntityNotFoundException;
import org.project.todo.config.JwtUtil;
import org.project.todo.user.dto.UserLogin;
import org.project.todo.user.dto.UserRequest;
import org.project.todo.user.dto.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse createUser(UserRequest userRequest) {

        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        User user = new User(userRequest);

        return new UserResponse(userRepository.save(user));
    }

    public String login(UserLogin userLogin) {
        User user = userRepository.findByEmail(userLogin.email());

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(userLogin.password(),  user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String token = jwtUtil.generateToken(user.getId());

        return token;
    }

    public UserResponse getUser(UUID id) {
        return userRepository.findById(id).map(UserResponse::new).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User getUserObject(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());

        if (!bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }

        return new UserResponse(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
