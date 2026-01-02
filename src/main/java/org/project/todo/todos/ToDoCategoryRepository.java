package org.project.todo.todos;

import org.project.todo.todos.dto.ToDoCategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToDoCategoryRepository extends JpaRepository<ToDoCategory, Long> {
    List<ToDoCategory> findByUser_Id(UUID userId);
}
