package com.waltbarr.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="phones")
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
