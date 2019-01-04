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
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorsService validationErrorsService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Validated(OnCreateChecks.class) @RequestBody Project project,
                                              BindingResult result, Principal principal) {

        ResponseEntity<?> errorsMap = validationErrorsService.mapValidationErrors(result);
        return errorsMap != null ? errorsMap :
                new ResponseEntity<>(projectService.saveProject(project, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@Validated(OnUpdateChecks.class) @RequestBody Project project,
                                           BindingResult result, Principal principal) {
        ResponseEntity<?> errorsMap = validationErrorsService.mapValidationErrors(result);
        return errorsMap != null ? errorsMap : new ResponseEntity<>(
                projectService.updateProject(project, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier,
                                                    Principal principal) {
        Project project = projectService.findProjectByIdentifier(projectIdentifier, principal.getName());
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier, Principal principal) {
        projectService.deleteProjectByIdentifier(projectIdentifier, principal.getName());
        return new ResponseEntity(
                "Project with ID: '" + projectIdentifier.toUpperCase() + "' was deleted succesfully.",
                HttpStatus.OK);
    }

}
