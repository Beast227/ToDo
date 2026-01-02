package org.project.todo.todos.dto;

import org.project.todo.todos.ToDoCategory;
import org.project.todo.user.User;

import java.util.UUID;

public record ToDoCategoryResponse(
        String category,
        String userId,
        String Id
) {

    public ToDoCategoryResponse(ToDoCategory toDoCategory, User user) {
        this(
                toDoCategory.getCategory(),
                user.getId().toString(),
                toDoCategory.getId().toString()
        );
    }

    public ToDoCategoryResponse(ToDoCategory toDoCategory) {
        this(
                toDoCategory.getCategory(),
                toDoCategory.getId().toString(),
                toDoCategory.getId().toString()
        );
    }

}
