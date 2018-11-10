package com.jskno.ppmtoolbe.services;

import com.jskno.ppmtoolbe.domain.Project;
import com.jskno.ppmtoolbe.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }
}
