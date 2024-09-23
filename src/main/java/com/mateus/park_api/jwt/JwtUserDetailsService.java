package com.mateus.park_api.jwt;

import com.mateus.park_api.entity.Usuario;
import com.mateus.park_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.loadUserByUsername(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokeAuthenticated(String userName){
        Usuario.Role role = usuarioService.findRoleByUsername(userName);
        return JwtUtils.createToken(userName, role.name().substring("ROLE_".length()));
    }
}
