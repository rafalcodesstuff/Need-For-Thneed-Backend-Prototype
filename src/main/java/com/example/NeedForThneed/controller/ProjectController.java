package com.example.NeedForThneed.controller;

import com.example.NeedForThneed.api.AbstractCRUDLApi;
import com.example.NeedForThneed.dto.ProjectDTO;
import com.example.NeedForThneed.entity.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController extends AbstractCRUDLController<Project, ProjectDTO> {
    public ProjectController(AbstractCRUDLApi<Project, ProjectDTO> api) {
        super(api);
    }
}
