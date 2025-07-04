package com.waltbarr.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.configuration.SecurityConfig;
import com.waltbarr.user.exceptions.EmailAlreadyExistsException;
import com.waltbarr.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import( SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        String token = UUID.randomUUID().toString();
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        UserDTO userRequest=  UserDTO.builder().name("test").email("test@dominio.cl").password("testPassword1!").build();
        UserResponseDTO user = UserResponseDTO.childBuilder().id(uuid).token(token).tokenExpiration(now.plusHours(24)).created(now).modified(now).isActive(true).lastLogin(now).build();

        when(userService.createUser(userRequest)).thenReturn(user);

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.data.id").value(uuid));

    }

    @Test
    public void testRegister_WrongPassword_BadRequest_Fail() throws Exception {
        String token = UUID.randomUUID().toString();
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        UserDTO userRequest=  UserDTO.builder().name("test").email("test@dominio.cl").password("test").build();
        UserResponseDTO user = UserResponseDTO.childBuilder().id(uuid).token(token).tokenExpiration(now.plusHours(24)).created(now).modified(now).isActive(true).lastLogin(now).build();

        when(userService.createUser(userRequest)).thenReturn(user);

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").value("Contraseña Invalida"));

    }

    @Test
    public void testRegister_EmailAlreadyExist_BadRequest_Fail() throws Exception {
        String token = UUID.randomUUID().toString();
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        UserDTO userRequest=  UserDTO.builder().name("test").email("test@dominio.cl").password("testPassword1!").build();

        // when(userService.findByToken(user.getToken())).thenReturn(user);
        when(userService.createUser(any(UserDTO.class))).thenThrow( new EmailAlreadyExistsException());

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").value("El correo ya esta registrado"));

    }

    @Test
    public void testInfo_Success() throws Exception {
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        UserResponseDTO user = UserResponseDTO.childBuilder().email("test@dominio.cl").tokenExpiration(now.plusHours(24)).build();
        UserResponseDTO infoUser = UserResponseDTO.childBuilder().name("Name Test").email(user.getEmail()).build();

        when(userService.findByToken(token)).thenReturn(Optional.of(user));
        when(userService.searchUserInfoByEmail(anyString())).thenReturn(Optional.of(infoUser));

        mockMvc.perform(get("/api/users/info").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+token ))
                 .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                    .andExpect(jsonPath("$.data.name").value(infoUser.getName()));
    }
}
