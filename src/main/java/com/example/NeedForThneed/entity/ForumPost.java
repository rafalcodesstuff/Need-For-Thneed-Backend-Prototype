package com.example.NeedForThneed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "forum_post")
public class ForumPost extends DistributedEntity {
    private String title;
}
