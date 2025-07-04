package com.waltbarr.user.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserResponseDTO extends UserDTO{
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private LocalDateTime tokenExpiration;
    private boolean isActive;

    @Builder(builderMethodName = "childBuilder")
    public UserResponseDTO(String name, String email, String password, List<PhoneDTO> phones, String id,LocalDateTime created ,LocalDateTime modified,LocalDateTime lastLogin, String token, LocalDateTime tokenExpiration, boolean isActive) {
        super(name, email, password, phones);
        this.id = id;
        this.created = created;
        this.modified= modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.tokenExpiration = tokenExpiration;
        this.isActive = isActive;
    }
}
