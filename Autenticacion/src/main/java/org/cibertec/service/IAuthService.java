package org.cibertec.service;

import org.cibertec.dto.LoginRequestDto;
import org.cibertec.dto.LoginResponseDto;
import org.cibertec.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(LoginRequestDto loginRequestDto);
}
