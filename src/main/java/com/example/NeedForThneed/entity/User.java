package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "userr")
public class User extends DistributedEntity {

    @Size(min = 2, max = 30)
    @NotBlank(message = "Username mustn't be null")
    @Pattern(regexp = "^\\w{1,30}$", message = "Name must be alpha-numeric (plus: \"_\") and max 30 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(min = 2, max = 60, message = "Password must be at least 8 - 60 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users")
//    @Column(name = "project_id")
    private List<Project> projects;

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
