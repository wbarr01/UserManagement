package com.waltbarr.user.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Información estandar para los API responses")
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    // usualmente se usa el flag success en los ApiResponses,
    // para el ejercicio lo omiti ya que se desea que los mensajes de error solo muestren el field de mensaje y su valor en el json
    //private boolean success;

    private String mensaje;
    private T data;
}
