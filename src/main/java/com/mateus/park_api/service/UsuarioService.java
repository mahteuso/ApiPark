package com.mateus.park_api.service;

import com.mateus.park_api.entity.Usuario;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;

import java.util.List;
import java.util.Optional;


public interface UsuarioService {
    List<UsuarioDtoResponse> findAll();
    UsuarioDtoResponse save(UsuarioDto dto);

    UsuarioDtoResponse findById(Long id);

    UsuarioDtoResponse update(UsuarioDto dto, Long id);

    void delete(Long id);

    void updateSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha);

    Usuario loadUserByUsername(String userName);

   Usuario.Role findRoleByUsername(String userName);
}
