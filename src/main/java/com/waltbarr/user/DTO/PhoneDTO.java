package com.waltbarr.user.DTO;

import lombok.Data;

@Data
public class PhoneDTO {
    private String number;
    private Integer cityCode;
    private Integer countryCode;
}
