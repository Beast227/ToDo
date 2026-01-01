package org.project.todo.todos;

import org.project.todo.todos.dto.ToDoCategoryRequest;
import org.project.todo.todos.dto.ToDoCategoryResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/create")
    public ToDoCategoryResponse createToDoCategory(@RequestBody ToDoCategoryRequest toDoCategoryRequest) {

        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return toDoService.createToDoCategory(toDoCategoryRequest, id);
    }

}
