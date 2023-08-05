package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "project")
public class Project extends DistributedEntity{
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
//    @Size(min = 1, message = "At least one user is required")
    @Column(name = "user_id")
    private List<User> users;

    @OneToOne
    @JoinColumn(name = "creator_id") //, nullable = false)
//    @NotNull(message = "Creator is required")
    private User creator;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 2000)
    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    public Project() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUser(List<User> users) {
        this.users = users;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
