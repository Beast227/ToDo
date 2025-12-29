package org.project.todo.user.dto;

import jakarta.validation.constraints.NotNull;
import org.project.todo.user.User;

import java.util.UUID;

public record UserResponse(
        String username,
        String email,
        UUID Id
) {

    public UserResponse(@NotNull User user) {
        this(
                user.getUsername(),
                user.getEmail(),
                user.getId()
        );
    }

}
