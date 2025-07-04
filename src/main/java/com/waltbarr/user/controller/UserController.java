package com.waltbarr.user.controller;

import com.waltbarr.user.DTO.ApiResponse;
import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
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
    @PostMapping(value="/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserDTO userRequest){

        UserResponseDTO user = userService.createUser(userRequest);
        return  new ResponseEntity<>(new ApiResponse<>("User Created", user), HttpStatus.CREATED);
    }

    /**
     * Get Current User information, I created it to test the Token Security. Must fail if token is not provided on request
     */
    @GetMapping(value="/info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDTO>> searchCurrentUserInfo(Authentication auth) {
        UserResponseDTO currentUser = (UserResponseDTO) auth.getPrincipal();
        Optional<UserResponseDTO> userOpt = userService.searchUserInfoByEmail(currentUser.getEmail());
        if (userOpt.isPresent()){
            return ResponseEntity.ok().body(new ApiResponse<>(null, userOpt.get()));
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
