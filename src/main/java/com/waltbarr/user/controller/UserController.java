package com.waltbarr.user.controller;

import com.waltbarr.user.DTO.ApiResponse;
import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "User management")
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
    @Operation(summary = "Registra un nuevo usuario", description = "Registra un nuevo usuario y retorna un token")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            )
    })
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
    @Operation(summary = "Muestra la información del usuario actual", description = "Retorna detalles del usuario en base al UUID token autenticado")
    @GetMapping(value="/info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDTO>> searchCurrentUserInfo(Authentication auth) {

        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>("Unauthorized",null));
        }

        UserResponseDTO currentUser = (UserResponseDTO) auth.getPrincipal();
        Optional<UserResponseDTO> userOpt = userService.searchUserInfoByEmail(currentUser.getEmail());
        if (userOpt.isPresent()){
            return ResponseEntity.ok().body(new ApiResponse<>(null, userOpt.get()));
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
