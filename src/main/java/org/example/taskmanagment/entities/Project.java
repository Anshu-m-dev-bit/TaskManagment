package org.example.taskmanagment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Changelog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToMany(mappedBy = "projects")
    Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.addProject(this);
        }
        return ;
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.removeProject(this);
        }
        return ;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
