package com.waltbarr.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Información de telefono asociado al usuario")
public class PhoneDTO {
    @Schema(example = "987654321", description = "Numero de teléfono")
    private String number;

    @Schema(example = "1", description = "Código de ciudad")
    private String cityCode;

    @Schema(example = "56", description = "Código de pais")
    private String countryCode;
}
