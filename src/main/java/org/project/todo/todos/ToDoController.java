package org.project.todo.todos;

import jakarta.validation.Valid;
import org.project.todo.todos.dto.ToDoCategoryRequest;
import org.project.todo.todos.dto.ToDoCategoryResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/category")
    public ToDoCategoryResponse createToDoCategory(@RequestBody @Valid ToDoCategoryRequest toDoCategoryRequest) {

        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return toDoService.createToDoCategory(toDoCategoryRequest, id);
    }

    @GetMapping("/category")
    public List<ToDoCategoryResponse> getToDoCategories() {
        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return toDoService.getToDoCategories(id);
    }

    @PutMapping("/category/{id}")
    public ToDoCategoryResponse updateToDoCategory(@RequestBody @Valid ToDoCategoryRequest toDoCategoryRequest, @PathVariable Long id) {
        return toDoService.updateToDoCategory(toDoCategoryRequest, id);
    }

    @DeleteMapping("/category")
    public void deleteToDoCategory(@RequestParam Long id) {
        toDoService.deleteToDoCategory(id);
    }

}
