package com.jskno.ppmtoolbe.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationErrorsService {

    public ResponseEntity<?> mapValidationErrors(BindingResult result) {

        if(result.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : result.getFieldErrors()){
                if(errorMap.get(error.getField()) == null) {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                } else {
                    errorMap.put(error.getField(),
                            errorMap.get(error.getField()).concat(". ").concat(error.getDefaultMessage()));
                }
            }
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
