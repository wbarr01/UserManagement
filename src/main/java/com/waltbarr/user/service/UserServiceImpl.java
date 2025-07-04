package com.waltbarr.user.service;

import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.entities.User;
import com.waltbarr.user.exceptions.EmailAlreadyExistsException;
import com.waltbarr.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
    }

    /**
     * Create User method
     */
    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {

        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if(userOptional.isPresent()){
           throw new EmailAlreadyExistsException();
        }

        LocalDateTime now =LocalDateTime.now();
        String passwordEncoded=passwordEncoder.encode(userDTO.getPassword());
        User newUser = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoded)
                .created(now)
                .last_login(now)
                .isActive(true)
                .token("")// FALTA TOKEN
                .build();
        newUser =userRepository.save(newUser);
        return UserResponseDTO.builder()
                .id(newUser.getId())
                .created(newUser.getCreated())
                .isActive(newUser.isActive())
                .last_login(newUser.getLast_login())
                .token(newUser.getToken()).build();

    }
}
