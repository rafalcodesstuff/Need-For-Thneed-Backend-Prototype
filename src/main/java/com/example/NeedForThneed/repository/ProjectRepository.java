package com.example.NeedForThneed.repository;

import com.example.NeedForThneed.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends DistributedRepository<Project> {
}
