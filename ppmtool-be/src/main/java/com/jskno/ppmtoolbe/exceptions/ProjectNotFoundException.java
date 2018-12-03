package com.jskno.ppmtoolbe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

    private String projectIdentifier;

    public ProjectNotFoundException(String message, String projectIdentifier) {
        super(message);
        this.projectIdentifier = projectIdentifier;
    }
}
