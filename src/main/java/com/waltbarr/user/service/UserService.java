package com.waltbarr.user.service;

import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;

import java.util.Optional;

public interface UserService {

    public UserResponseDTO createUser(UserDTO user);
    public Optional<UserResponseDTO> findByToken(String token);
    public Optional<UserResponseDTO> searchUserInfoByEmail(String email);
}
