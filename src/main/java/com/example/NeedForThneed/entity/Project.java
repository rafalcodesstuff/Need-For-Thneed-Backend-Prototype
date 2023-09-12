package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 2000)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Total Value must not be null")
    @Min(value = 0, message = "Total Value must not be less than 0")
    @Transient
    @Column(name = "total_value")
    private Integer totalValue;

    @OneToOne
    @JoinColumn(name = "creator_id") //, nullable = false)
    private User creator;

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
    @JoinColumn(name = "pool_id")
    private MoneyPool pool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    // this is here because I need to update the total value somehow
    @OneToMany
    @JoinColumn(name = "thneed_id")
    private List<Thneed> thneeds;

    public Project() {
        super();
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

    public Integer getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
