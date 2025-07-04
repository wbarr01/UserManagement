package com.waltbarr.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(nullable = false, unique=true)
    private String email;

    private String password;//FALTA ENCRIPTARLO
    private String token;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;
    private boolean isActive;
}
