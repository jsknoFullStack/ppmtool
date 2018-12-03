package com.jskno.ppmtoolbe.web;

import com.jskno.ppmtoolbe.domain.Project;
import com.jskno.ppmtoolbe.domain.validation.OnCreateChecks;
import com.jskno.ppmtoolbe.domain.validation.OnUpdateChecks;
import com.jskno.ppmtoolbe.services.ProjectService;
import com.jskno.ppmtoolbe.services.ValidationErrorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorsService validationErrorsService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Validated(OnCreateChecks.class) @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorsMap = validationErrorsService.mapValidationErrors(result);
        return errorsMap != null ? errorsMap : new ResponseEntity<>(projectService.saveProject(project), HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        Project project = projectService.findProjectByIdentifier(projectIdentifier);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity(
                "Project with ID: '" + projectIdentifier.toUpperCase() + "' was deleted succesfully.",
                HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@Validated(OnUpdateChecks.class) @RequestBody Project project, BindingResult result) {
         ResponseEntity<?> errorsMap = validationErrorsService.mapValidationErrors(result);
         return errorsMap != null ? errorsMap : new ResponseEntity<>(
                 projectService.updateProject(project), HttpStatus.OK);
    }
}
