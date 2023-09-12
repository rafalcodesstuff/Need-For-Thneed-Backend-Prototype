package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "thread")
public class Threadd extends DistributedEntity {

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    private Boolean completed;

    @NotNull(message = "Votes must not be null")
    @Min(value = 0, message = "Votes must not be less than 0")
    @Column(name = "votes")
    private Integer votes;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @OneToOne
    @JoinColumn(name = "pool_id")
    private MoneyPool pool;

    @ManyToOne
    private Thneed thneed;

    @OneToMany
    private List<Stitch> stitches;

    public Threadd() {
        super();
    }

}
