package com.waltbarr.user.DTO;

import com.waltbarr.user.customValidation.PasswordValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "Request data para usuario al registrarse")
public class UserDTO {


    @Schema(example="Juan Rodriguez", description="Nombre completo del usuario")
    @NotBlank(message = "el campo Nombre no puede estar vacio")
    private String name;

    @Schema(example="juan@dominio.cl", description="Email del usuario")
    @Pattern(
            regexp= "^[a-z]+@dominio\\.cl$",
            message= "Email debe seguir el siguiente formato aaaaaaa@dominio.cl"
    )
    private String email;

    @Schema(example="Hunter!11", description="Contraseña del Usuario")
    @PasswordValidation
    private String password;

    @Schema(description = "Lista de teléfonos del usuario")
    private List<PhoneDTO> phones = new ArrayList<>();
}
