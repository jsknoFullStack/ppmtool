package com.jskno.ppmtoolbe.services;

import com.jskno.ppmtoolbe.domain.Backlog;
import com.jskno.ppmtoolbe.domain.Project;
import com.jskno.ppmtoolbe.domain.ProjectTask;
import com.jskno.ppmtoolbe.exceptions.ProjectNotFoundException;
import com.jskno.ppmtoolbe.repositories.BacklogRepository;
import com.jskno.ppmtoolbe.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

//        Backlog backlog = this.backlogRepository.findByProjectIdentifier(projectIdentifier);
//        if(backlog == null) {
//            throw new ProjectNotFoundException("Project with project identifer: " + projectIdentifier + " not found", projectIdentifier);
//        }

        Backlog backlog = this.projectService.findProjectByIdentifier(projectIdentifier).getBacklog();
        backlog.setPTSequence(backlog.getPTSequence() + 1);
        projectTask.setBacklog(backlog);
        projectTask.setProjectSequence(projectIdentifier + "-" + backlog.getPTSequence());
        projectTask.setProjectIdentifier(projectIdentifier);
        if(projectTask.getPriority() == null || projectTask.getPriority().equals(0)) {
           projectTask.setPriority(3);
        }
        if(StringUtils.isEmpty(projectTask.getStatus())) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogProjectTasks(String backlogId) {
        projectService.findProjectByIdentifier(backlogId);
        return this.projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectSequence) {

        projectService.findProjectByIdentifier(backlogId);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project task with sequence: "
                    + projectSequence + " not found", projectSequence);
        }
        if(!backlogId.equals(projectTask.getProjectIdentifier())) {
            throw new ProjectNotFoundException("Project task '" + projectSequence +
                    "' does not exist in project '" + backlogId + "'", backlogId);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId,
                                               String projectSequence) {

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectSequence);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deleteProjectTaskByProjectSequence(String backlogId, String projectSequence) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectSequence);
        projectTaskRepository.delete(projectTask);
    }
}
