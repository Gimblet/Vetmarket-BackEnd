package org.cibertec.service;

import org.cibertec.dto.LoginRequestDto;
import org.cibertec.dto.LoginResponseDto;

public interface IAuthService {
    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
}
