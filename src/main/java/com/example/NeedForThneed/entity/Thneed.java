package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "thneed")
public class Thneed extends DistributedEntity {

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Transient
    @NotNull(message = "Total Value must not be null")
    @Min(value = 0, message = "Total Value must not be less than 0")
    @Column(name = "total_value", nullable = false)
    private Integer totalValue;

    private Boolean completed;

    @NotNull(message = "Votes must not be null")
    @Min(value = 0, message = "Votes must not be less than 0")
    @Column(name = "votes")
    private Integer votes;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "thneed_user",
            joinColumns = @JoinColumn(name = "thneed_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "contributer_id")
    private List<User> contributors;

    @OneToOne
    private Forum forum;

    @OneToOne
    @JoinColumn(name = "pool_id")
    private MoneyPool pool;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany
    private List<Threadd> threadd;

}
