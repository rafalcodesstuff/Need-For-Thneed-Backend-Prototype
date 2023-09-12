package com.example.NeedForThneed.service;

import com.example.NeedForThneed.api.ProjectApi;
import com.example.NeedForThneed.dto.ProjectDTO;
import com.example.NeedForThneed.entity.Project;
import com.example.NeedForThneed.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends AbstractCRUDLService<Project, ProjectDTO> implements ProjectApi {
    @Autowired
    public ProjectService(final ProjectRepository repository) {
        super(repository);
    }
}
