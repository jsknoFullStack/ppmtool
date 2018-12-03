package com.jskno.ppmtoolbe.services;

import com.jskno.ppmtoolbe.domain.Backlog;
import com.jskno.ppmtoolbe.domain.Project;
import com.jskno.ppmtoolbe.exceptions.ProjectIdException;
import com.jskno.ppmtoolbe.repositories.BacklogRepository;
import com.jskno.ppmtoolbe.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveProject(Project project) {
        try {
            normalizeProjectIdentifier(project);
            project.setBacklog(new Backlog(project));
            return save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    public Project updateProject(Project project) {
        try {
            normalizeProjectIdentifier(project);
            project.setBacklog(this.backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
            return save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    private void normalizeProjectIdentifier(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
    }

    private Project save(Project project) {
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
        return projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Cannot delete project with ID '" + projectIdentifier.toUpperCase() + "'. This project does not exist");
        }
        projectRepository.delete(project);
    }
}
