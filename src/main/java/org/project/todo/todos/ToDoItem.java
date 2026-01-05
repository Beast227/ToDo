package org.project.todo.todos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.project.todo.todos.dto.ToDoItemRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "todo_items")
public class ToDoItem {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dueDate;

    private String title;

    @ElementCollection
    @CollectionTable(
            name = "todo_steps",
            joinColumns = @JoinColumn(name = "todo_item_id")
    )
    @Column(name = "step")
    private List<String> steps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",  nullable = false)
    private ToDoCategory category;

    private Boolean completed;

    protected ToDoItem() {}

    public ToDoItem(LocalDateTime dueDate, String title, List<String> steps, ToDoCategory category) {
        this.dueDate = dueDate;
        this.title = title;
        this.steps = steps != null ? steps : new ArrayList<>();
        this.category = category;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSteps() {
        return steps;
    }

    public ToDoCategory getCategory() {
        return category;
    }

    public void setCategory(ToDoCategory category) {
        this.category = category;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void updateFrom(ToDoItemRequest toDoItemRequest) {
        if (this.dueDate != toDoItemRequest.dueDate() && toDoItemRequest.dueDate() != null) {
            this.dueDate = toDoItemRequest.dueDate();
        }
        if (this.title != toDoItemRequest.title() && toDoItemRequest.title() != null) {
            this.title = toDoItemRequest.title();
        }
        if (this.steps != toDoItemRequest.steps() && toDoItemRequest.steps() != null) {
            this.steps = toDoItemRequest.steps();
        }
        if (this.completed != toDoItemRequest.completed() && toDoItemRequest.completed() != null) {
            this.completed = toDoItemRequest.completed();
        }
    }

}
