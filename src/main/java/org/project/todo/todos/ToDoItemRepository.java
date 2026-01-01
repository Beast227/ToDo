package org.project.todo.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoItemRepository  extends JpaRepository<ToDoItem, Long> {
}
