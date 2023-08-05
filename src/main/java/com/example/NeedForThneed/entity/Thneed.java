package com.example.NeedForThneed.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "thneed")
public class Thneed extends DistributedEntity {
    private String title;
    private String description;

    // da fuq? is this possible
    // TODO
    private HashMap<User, Integer> pool;

    @ManyToOne
    private User creator;

    @OneToOne
    private Forum forum;

    @ManyToOne
    private Project project;

    @OneToMany
    private Threadd threadd;



}
