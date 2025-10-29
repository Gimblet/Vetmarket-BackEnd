package org.cibertec.service.impl;

import lombok.RequiredArgsConstructor;

import org.cibertec.dto.LoginRequestDto;
import org.cibertec.dto.LoginResponseDto;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.IUsuarioRepository;
import org.cibertec.security.util.JwtUtil;
import org.cibertec.service.IAuthService;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final JwtUtil jwtUtil;
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(LoginRequestDto loginRequestDto) {
        // Obtener el usuario completo para obtener el ID
        Usuario usuario = usuarioRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // validar la contraseña
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), usuario.getPassword())) {
            ApiResponse<LoginResponseDto> response =
                    new ApiResponse<>(false, "Contraseña incorrecta", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // generar token con info del usuario
        String token = jwtUtil.generateTokenWithUserInfo(usuario);
        Long expiration = jwtUtil.extractExpiration(token).getTime();

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_ANONIMO")
                .usuarioId(usuario.getIdUsuario())
                .expirateAt(expiration)
                .build();

        ApiResponse<LoginResponseDto> response =
                new ApiResponse<>(true, "Inicio de sesión exitoso", loginResponse);

        return ResponseEntity.ok(response);
    }
}
