package com.waltbarr.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Lazy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime created;
    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private boolean isActive;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();

    //salvando token en Tabla User ya que el ejercicio menciona que debe ser guardado junto con el usuario.
    // Una mejora seria guardarlo en una tabla distinta para Tokens por usuario
    private String token;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;
}
