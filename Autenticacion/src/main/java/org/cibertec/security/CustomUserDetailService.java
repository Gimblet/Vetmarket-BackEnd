package org.cibertec.security;

import lombok.RequiredArgsConstructor;
import org.cibertec.entity.Usuario;
import org.cibertec.repository.IUsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final IUsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no registrado"));

        if(usuario.getRol() == null) {
            throw new UsernameNotFoundException("Usuario sin rol asignado: " + username);
        }

        // convierte el nombre del rol e un GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol()));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities
        );
    }
}
