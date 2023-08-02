package com.example.NeedForThneed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "forum")
public class Forum extends DistributedEntity {

    @OneToOne
    private Project project;


}
