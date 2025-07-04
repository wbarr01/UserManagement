package com.waltbarr.user.repository;

import com.waltbarr.user.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
}
