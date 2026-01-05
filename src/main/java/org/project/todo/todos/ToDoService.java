package org.project.todo.todos;

import jakarta.transaction.Transactional;
import org.project.todo.todos.dto.ToDoCategoryRequest;
import org.project.todo.todos.dto.ToDoCategoryResponse;
import org.project.todo.todos.dto.ToDoItemRequest;
import org.project.todo.todos.dto.ToDoItemResponse;
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

    @Transactional
    public ToDoItemResponse createToDoItem(ToDoItemRequest toDoItemRequest, Long id, UUID userId) {
        ToDoCategory toDoCategory = toDoCategoryRepository.findById(id).get();

        if (!toDoCategory.getUser().getId().equals(userId)) {
            throw new RuntimeException("ToDoCategory Not Found");
        }

        ToDoItem toDoItem = new ToDoItem(
                toDoItemRequest.dueDate(),
                toDoItemRequest.title(),
                toDoItemRequest.steps(),
                null // IMPORTANT
        );

        toDoCategory.addItem(toDoItem);

        return new ToDoItemResponse(toDoItem, toDoCategory);
    }

    public ToDoItemResponse updateToDoItem(ToDoItemRequest toDoItemRequest, Long id) {
        ToDoItem toDoItem = toDoItemRepository.findById(id).get();

        toDoItem.updateFrom(toDoItemRequest);

        toDoItemRepository.save(toDoItem);

        return new ToDoItemResponse(toDoItem);
    }

    public ToDoItemResponse updateToDoItemCategory(Long ItemId, Long CatId, UUID id) {
        ToDoItem toDoItem = toDoItemRepository.findById(ItemId).get();
        ToDoCategory toDoCategory = toDoCategoryRepository.findById(CatId).get();

        if (!toDoCategory.getUser().getId().equals(id)) {
            throw new RuntimeException("ToDoCategory Not Found");
        }

        toDoItem.setCategory(toDoCategory);

        toDoItemRepository.save(toDoItem);

        return new ToDoItemResponse(toDoItem, toDoCategory);
    }

    @Transactional
    public ToDoItemResponse deleteToDoItem(Long ItemId, Long CatId, UUID id) {
        ToDoCategory toDoCategory = toDoCategoryRepository.findById(CatId).get();

        if (!toDoCategory.getUser().getId().equals(id)) {
            throw new RuntimeException("ToDoCategory Not Found");
        }

        ToDoItem toDoItem = toDoCategory.getItems().stream()
                .filter(t -> t.getId().equals(ItemId))
                .findFirst().get();

        toDoCategory.removeItem(toDoItem);

        return new ToDoItemResponse(toDoItem);
    }

    public List<ToDoItemResponse> getToDoItems(Long CatId, UUID id) {
        ToDoCategory toDoCategory = toDoCategoryRepository.findById(CatId).get();

        if (!toDoCategory.getUser().getId().equals(id)) {
            throw new RuntimeException("ToDoCategory Not Found");
        }

        return toDoCategory.getItems().stream().map(item -> new ToDoItemResponse(item, toDoCategory)).toList();
    }


}
