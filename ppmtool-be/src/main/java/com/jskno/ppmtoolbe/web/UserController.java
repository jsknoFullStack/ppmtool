package com.jskno.ppmtoolbe.web;

import com.jskno.ppmtoolbe.security.JwtTokenProvider;
import com.jskno.ppmtoolbe.security.domain.User;
import com.jskno.ppmtoolbe.security.domain.UserValidator;
import com.jskno.ppmtoolbe.security.payload.JWTLoginSucessReponse;
import com.jskno.ppmtoolbe.security.payload.LoginRequest;
import com.jskno.ppmtoolbe.security.services.UserService;
import com.jskno.ppmtoolbe.security.utils.SecurityConstants;
import com.jskno.ppmtoolbe.services.ValidationErrorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private ValidationErrorsService validationErrorsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        if(errorMap != null) {
            return errorMap;
        }

        User newUser = userService.saveUser(user);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorsService.mapValidationErrors(bindingResult);
        if(errorMap != null) {
            return errorMap;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

}
