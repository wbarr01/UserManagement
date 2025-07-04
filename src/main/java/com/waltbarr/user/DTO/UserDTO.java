package com.waltbarr.user.DTO;

import com.waltbarr.user.customValidation.PasswordValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDTO {


    @NotBlank(message = "el campo Nombre no puede estar vacio")
    private String name;

    @Pattern(
            regexp= "^[a-z]+@dominio\\.cl$",
            message= "Email debe seguir el siguiente formato aaaaaaa@dominio.cl"
    )
    private String email;

    @PasswordValidation
    private String password;

    @Builder.Default
    private List<PhoneDTO> phones = new ArrayList<>();
}
