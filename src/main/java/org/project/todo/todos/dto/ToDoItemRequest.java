package org.project.todo.todos.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record ToDoItemRequest(
        LocalDateTime dueDate,
        @NotBlank String title,
        List<String> steps,
        Boolean completed
) {



}
