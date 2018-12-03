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
import java.util.List;

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
                                                     BindingResult bindingResult, @PathVariable String backlogId) {

        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        return errorMap != null ? errorMap : new ResponseEntity(projectTaskService.addProjectTask(backlogId, projectTask), HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getBacklogProjectTasks(@PathVariable String backlogId) {
        return projectTaskService.findBacklogProjectTasks(backlogId);
    }

    @GetMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String projectSequence) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlogId, projectSequence);
        return new ResponseEntity(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedTask,
            BindingResult bindingResult, @PathVariable String backlogId, @PathVariable String projectSequence) {

        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        return errorMap != null ? errorMap : new ResponseEntity(projectTaskService.updateByProjectSequence(
                updatedTask, backlogId, projectSequence), HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{projectSequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String projectSequence) {
        projectTaskService.deleteProjectTaskByProjectSequence(backlogId, projectSequence);
        return new ResponseEntity("Project task '" + projectSequence + "' was deleted successfully", HttpStatus.OK);
    }
}
