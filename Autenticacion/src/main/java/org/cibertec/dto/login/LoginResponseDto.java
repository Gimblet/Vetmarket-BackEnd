package org.cibertec.dto.login;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private String username;
    private String rol;
    private Long usuarioId;
    private Long expirateAt;
}
