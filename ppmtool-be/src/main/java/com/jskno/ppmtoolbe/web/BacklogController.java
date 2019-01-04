package com.jskno.ppmtoolbe.web;

import com.jskno.ppmtoolbe.domain.ProjectTask;
import com.jskno.ppmtoolbe.services.ProjectTaskService;
import com.jskno.ppmtoolbe.services.ValidationErrorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ValidationErrorsService validationErrorsService;

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                             BindingResult bindingResult, @PathVariable String backlogId, Principal principal) {

        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        return errorMap != null ? errorMap :
                new ResponseEntity(projectTaskService.addProjectTask(backlogId, projectTask, principal.getName()),
                        HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getBacklogProjectTasks(@PathVariable String backlogId, Principal principal) {
        return projectTaskService.findBacklogProjectTasks(backlogId, principal.getName());
    }

    @GetMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String projectSequence,
                                            Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlogId,
                projectSequence, principal.getName());
        return new ResponseEntity(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedTask,
            BindingResult bindingResult, @PathVariable String backlogId,
           @PathVariable String projectSequence, Principal principal) {

        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        return errorMap != null ? errorMap : new ResponseEntity(projectTaskService.updateByProjectSequence(
                updatedTask, backlogId, projectSequence, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String projectSequence,
                               Principal principal) {
        projectTaskService.deleteProjectTaskByProjectSequence(backlogId, projectSequence, principal.getName());
        return new ResponseEntity("Project task '" + projectSequence + "' was deleted successfully", HttpStatus.OK);
    }
}
