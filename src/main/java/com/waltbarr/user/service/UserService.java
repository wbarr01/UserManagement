package com.waltbarr.user.service;

import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;

public interface UserService {

    public UserResponseDTO createUser(UserDTO user);
    public UserResponseDTO findByToken(String token);
}
