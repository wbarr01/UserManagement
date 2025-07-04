package com.waltbarr.user.service;

import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.entities.User;
import com.waltbarr.user.exceptions.EmailAlreadyExistsException;
import com.waltbarr.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class UserServiceTest {


    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserService userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository,passwordEncoder);
    }

    @Test
    public void testCreateUserSuccess(){
        UserDTO userRequest=  UserDTO.builder().name("test").email("test@dominio.cl").password("testPassword").build();
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        User newUser = User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .id(String.valueOf(UUID.randomUUID()))
                .build();
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        UserResponseDTO result= userService.createUser(userRequest);
        assertNotNull(result);
        assertEquals(newUser.getId(),result.getId());
        verify(userRepository).save((any(User.class)));
    }

    @Test
    public void testCreateUser_EmailAlreadyExistsException(){
        UserDTO userRequest=  UserDTO.builder().name("test").email("test@dominio.cl").password("testPassword").build();
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(new User()));

        EmailAlreadyExistsException e =  assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userRequest));
        assertEquals("El correo ya esta registrado",e.getMessage());
    }
}
