package org.cibertec.service;

import org.cibertec.dto.login.LoginRequestDto;
import org.cibertec.dto.login.LoginResponseDto;

public interface IAuthService {
    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
}
