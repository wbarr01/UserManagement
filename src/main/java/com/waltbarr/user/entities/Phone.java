package com.waltbarr.user.entities;

import jakarta.persistence.*;

@Entity
@Table(name ="Phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private Integer cityCode;
    private Integer countryCode;
}
