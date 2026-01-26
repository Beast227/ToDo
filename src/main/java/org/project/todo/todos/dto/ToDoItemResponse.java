package org.project.todo.todos.dto;

import jakarta.validation.constraints.NotBlank;
import org.project.todo.todos.ToDoCategory;
import org.project.todo.todos.ToDoItem;
import org.project.todo.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record ToDoItemResponse(
        Long id,
        LocalDateTime dueDate,
        @NotBlank String title,
        List<String> steps,
        @NotBlank String category,
        Boolean completed
) {
    public ToDoItemResponse(ToDoItem toDoItem, ToDoCategory toDoCategory) {
        this(
                toDoItem.getId(),
                toDoItem.getDueDate(),
                toDoItem.getTitle(),
                toDoItem.getSteps(),
                toDoCategory.getCategory(),
                toDoItem.getCompleted()
        );
    }

    public ToDoItemResponse(ToDoItem toDoItem) {
        this(
                toDoItem.getId(),
                toDoItem.getDueDate(),
                toDoItem.getTitle(),
                null,
                null,
                toDoItem.getCompleted()
        );
    }
}
