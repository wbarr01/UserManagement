package com.waltbarr.user.service;

import com.waltbarr.user.DTO.PhoneDTO;
import com.waltbarr.user.DTO.UserDTO;
import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.entities.Phone;
import com.waltbarr.user.entities.User;
import com.waltbarr.user.exceptions.EmailAlreadyExistsException;
import com.waltbarr.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
     * Create a new User,  if email doesn't already exist
     */
    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {

        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if(userOptional.isPresent()){
           throw new EmailAlreadyExistsException();
        }

        String passwordEncoded=passwordEncoder.encode(userDTO.getPassword());

        User newUser = convertDTOToUser(userDTO);
        newUser.setPassword(passwordEncoded);
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setTokenExpiration(newUser.getCreated().plusHours(24));

        newUser =userRepository.save(newUser);

        UserResponseDTO userResponseDTO =UserResponseDTO.childBuilder()
                .id(newUser.getId())
                .created(newUser.getCreated())
                .modified(newUser.getModified())
                .isActive(newUser.isActive())
                .lastLogin(newUser.getLastLogin())
                .token(newUser.getToken()).build();

        return userResponseDTO;
    }

    /**
     *
     *
     */
    @Override
    public Optional<UserResponseDTO> findByToken(String token) {
        Optional<User> optUser=userRepository.findByToken(token);
        if(optUser.isPresent()){
            User user=optUser.get();
            return Optional.of(UserResponseDTO.childBuilder().email(user.getEmail()).tokenExpiration(user.getTokenExpiration()).build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserResponseDTO> searchUserInfoByEmail(String email) {
        Optional<User> optUser=userRepository.findByEmail(email);
        if(optUser.isPresent()){
            User user=optUser.get();
            return Optional.of(UserResponseDTO.childBuilder().name(user.getName()).email(user.getEmail()).isActive(user.isActive()).build());
        }
        return Optional.empty();
    }

    /**
     * Convert a UserDTO to User entity
     */
    private User convertDTOToUser(UserDTO userDTO){
        LocalDateTime now =LocalDateTime.now();

        User newUser = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .created(now)
                .modified(now)
                .lastLogin(now)
                .isActive(true)
                .build();

        if (userDTO.getPhones()!= null){
            for(PhoneDTO phoneDto : userDTO.getPhones()){
                Phone phone = new Phone();
                phone.setNumber(phoneDto.getNumber());
                phone.setCityCode(phoneDto.getCityCode());
                phone.setCountryCode(phoneDto.getCountryCode());
                phone.setUser(newUser);
                newUser.getPhones().add(phone);
            }
        }

        return newUser;
    }
}
