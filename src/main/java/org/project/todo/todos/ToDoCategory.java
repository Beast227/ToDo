package org.project.todo.todos;

import jakarta.persistence.*;
import org.project.todo.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "todo_categories")
public class ToDoCategory{

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ToDoItem> items;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    protected ToDoCategory() {}

    public ToDoCategory(String category, User user) {
        this.category = category;
        this.user = user;
    }

    public Long getId() {
         return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public List<ToDoItem> getItems() {
        return items;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // helper functions

    public void addItem(ToDoItem item) {
        items.add(item);
    }

    public void removeItem(ToDoItem item) {
        items.remove(item);
    }

}