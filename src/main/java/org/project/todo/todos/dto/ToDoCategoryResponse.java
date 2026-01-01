package org.project.todo.todos.dto;

import org.project.todo.todos.ToDoCategory;
import org.project.todo.user.User;

public record ToDoCategoryResponse(
        String category,
        String userId
) {

    public ToDoCategoryResponse(ToDoCategory toDoCategory, User user) {
        this(
                toDoCategory.getCategory(),
                user.getId().toString()
        );
    }

}
