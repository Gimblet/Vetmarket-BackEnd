package org.cibertec.controller;

import lombok.RequiredArgsConstructor;

import org.cibertec.dto.LoginRequestDto;
import org.cibertec.dto.LoginResponseDto;
import org.cibertec.service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = authService.authenticate(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
