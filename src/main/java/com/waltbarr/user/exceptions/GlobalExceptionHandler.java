package com.waltbarr.user.exceptions;

import com.waltbarr.user.DTO.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailExists(EmailAlreadyExistsException ex){
        return ResponseEntity.badRequest().body(new ApiResponse<>( ex.getMessage(),null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralRuntime(RuntimeException ex){
        return ResponseEntity.internalServerError().body(new ApiResponse<>("Internal Server error",null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map< String, String> errors = new HashMap<>();
        StringBuilder errorMessage= new StringBuilder();
        String separation ="";
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            errorMessage.append(separation);
            separation = ", ";
            errorMessage.append(fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(errorMessage.toString(), null));
    }
}
