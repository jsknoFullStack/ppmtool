package com.jskno.ppmtoolbe.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

@Service
public class ValidationErrorsService {

    public ResponseEntity<?> mapValidationErrors(BindingResult result) {

        if(result.hasErrors()) {
            return new ResponseEntity<>(
                    result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)),
                    HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
