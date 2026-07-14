package org.example.taskmanagment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = true)
    private String description;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrStatus status;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrPriority priority;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum CurrStatus {
        TODO,
        IN_PROGRESS,
        DONE
    };
    public enum CurrPriority {
        LOW,
        MEDIUM,
        HIGH
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public CurrStatus getStatus() {
        return status;
    }
    public void setStatus(CurrStatus status) {
        this.status = status;
    }

    public CurrPriority getPriority() {
        return priority;
    }

    public void setPriority(CurrPriority priority) {
        this.priority = priority;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
