package com.example.NeedForThneed.dto;

import com.example.NeedForThneed.entity.Project;

import java.util.List;

@Dto
public class UserDTO extends AbstractBaseDTO {
    private String username;
    private String password;
    private List<Project> projects;

    public UserDTO() {
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
