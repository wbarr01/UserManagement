package com.waltbarr.user.controller;

import com.waltbarr.user.DTO.ApiResponse;
import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.exceptions.EmailAlreadyExistsException;
import com.waltbarr.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * User Service
     */
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create User endpoint
     */
    @PostMapping(value="/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserDTO userRequest){

        UserResponseDTO user = userService.createUser(userRequest);
        return  new ResponseEntity<>(new ApiResponse<>("User Created", user), HttpStatus.CREATED);
    }
}
