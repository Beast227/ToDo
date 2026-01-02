package org.project.todo.todos;

import org.project.todo.todos.dto.ToDoCategoryRequest;
import org.project.todo.todos.dto.ToDoCategoryResponse;
import org.project.todo.user.User;
import org.project.todo.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {

    private final ToDoCategoryRepository toDoCategoryRepository;
    private final ToDoItemRepository toDoItemRepository;
    private final UserService userService;

    public ToDoService(ToDoCategoryRepository toDoCategoryRepository, ToDoItemRepository toDoItemRepository, UserService userService) {
        this.toDoCategoryRepository = toDoCategoryRepository;
        this.toDoItemRepository = toDoItemRepository;
        this.userService = userService;
    }

    public ToDoCategoryResponse createToDoCategory(ToDoCategoryRequest toDoCategoryRequest, UUID id) {
        User user = userService.getUserObject(id);
        ToDoCategory toDoCategory = new ToDoCategory(toDoCategoryRequest.category(), user);

        toDoCategoryRepository.save(toDoCategory);

        return new ToDoCategoryResponse(toDoCategory, user);
    }

//    public ToDoItemResponse createToDoItem(ToDoItemRequest toDoItemRequest) {
//
//    }

    public List<ToDoCategoryResponse> getToDoCategories(UUID id) {
        return toDoCategoryRepository.findByUser_Id(id).stream().map((todo) -> new ToDoCategoryResponse(todo)).toList();
    }

    public ToDoCategoryResponse updateToDoCategory(ToDoCategoryRequest toDoCategoryRequest, Long id) {
        ToDoCategory toDoCategory = toDoCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("ToDoCategory Not Found"));
        toDoCategory.setCategory(toDoCategoryRequest.category());

        toDoCategoryRepository.save(toDoCategory);

        return new ToDoCategoryResponse(toDoCategory);
    }

    public void deleteToDoCategory(Long id) {
        toDoCategoryRepository.deleteById(id);
    }

}
