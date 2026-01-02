package org.project.todo.todos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ToDoCategoryRequest(
        @NotNull @NotBlank String category
) {
}
