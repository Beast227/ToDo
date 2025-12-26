package org.project.todo.todos;

import jakarta.persistence.*;

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

    public String getTitle() {
        return title;
    }

    public List<String> getSteps() {
        return steps;
    }

    public ToDoCategory getCategory() {
        return category;
    }

    public Boolean getCompleted() {
        return completed;
    }

}
