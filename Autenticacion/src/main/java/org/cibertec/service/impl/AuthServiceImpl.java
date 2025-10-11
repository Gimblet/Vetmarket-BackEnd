package org.cibertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.cibertec.dto.login.LoginRequestDto;
import org.cibertec.dto.login.LoginResponseDto;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.IRolRepository;
import org.cibertec.repository.IUsuarioRepository;
import org.cibertec.security.util.JwtUtil;
import org.cibertec.service.IAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final IRolRepository rolRepository;
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String token = jwtUtil.generateToken(user);
        long expiration = jwtUtil.extractExpiration(token).getTime();

        // Obtener el usuario completo para obtener el ID
        Usuario usuario = usuarioRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_CLIENTE");

        return LoginResponseDto.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(rol)
                .usuarioId(usuario.getIdUsuario())
                .expirateAt(expiration)
                .build();
    }
}
