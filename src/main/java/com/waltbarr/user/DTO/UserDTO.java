package com.waltbarr.user.DTO;

import com.waltbarr.user.customValidation.PasswordValidation;
import lombok.*;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Data
@Builder
public class UserDTO {


    private String name;
    @Pattern(
            regexp= "^[a-z]+@dominio\\.cl$",
            message= "Email debe seguir el siguiente formato aaaaaaa@dominio.cl"
    )
    private String email;

    @PasswordValidation
    private String password;
    private List<PhoneDTO> phones;
}
