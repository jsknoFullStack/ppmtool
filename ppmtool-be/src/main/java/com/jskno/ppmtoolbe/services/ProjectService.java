package com.jskno.ppmtoolbe.services;

import com.jskno.ppmtoolbe.domain.Backlog;
import com.jskno.ppmtoolbe.domain.Project;
import com.jskno.ppmtoolbe.exceptions.ProjectIdException;
import com.jskno.ppmtoolbe.exceptions.ProjectNotFoundException;
import com.jskno.ppmtoolbe.repositories.BacklogRepository;
import com.jskno.ppmtoolbe.repositories.ProjectRepository;
import com.jskno.ppmtoolbe.security.domain.User;
import com.jskno.ppmtoolbe.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveProject(Project project, String username) {
        try {

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            normalizeProjectIdentifier(project);
            project.setBacklog(new Backlog(project));
            return save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    public Project updateProject(Project project, String username) {

        Project existingProject = this.findProjectByIdentifier(project.getProjectIdentifier(), username);
        if(project.getId().equals(existingProject.getId())) {
            throw new ProjectIdException("Existing DDBB Project id '" +
                    existingProject.getId() + "' does not match the Updated Project " + project.getId());
        }
        project.setUser(existingProject.getUser());
        project.setProjectLeader(existingProject.getProjectLeader());

        normalizeProjectIdentifier(project);
        project.setBacklog(existingProject.getBacklog());
        return save(project);

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

    public Project findProjectByIdentifier(String projectIdentifier, String username) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exist");
        }

        if(!username.equals(project.getProjectLeader())) {
            throw new ProjectNotFoundException("Project ID '" + projectIdentifier +
                    "' not found in your account", projectIdentifier);
        }
        return project;
    }

    public Project findProjectByIdentifier(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectIdentifier, String username) {
        Project project = this.findProjectByIdentifier(projectIdentifier, username);
        projectRepository.delete(project);
    }
}
