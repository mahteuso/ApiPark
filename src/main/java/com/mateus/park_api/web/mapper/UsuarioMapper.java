package com.mateus.park_api.web.mapper;

import com.mateus.park_api.entity.Usuario;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;

public class UsuarioMapper {

    public static Usuario fromDtoToEntity(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .role(dto.getRole())
                .password(dto.getPassword())
                .dataCriacao(dto.getDataCriacao())
                .dataModificacao(dto.getDataModificacao())
                .criadoPor(dto.getModificadoPor())
                .modificadoPor(dto.getModificadoPor())
                .build();
    }

    public static Usuario fromDtoToEntityUpdate(UsuarioDto dto, Long id) {
        return Usuario.builder()
                .id(id)
                .userName(dto.getUserName())
                .role(dto.getRole())
                .password(dto.getPassword())
                .dataCriacao(dto.getDataCriacao())
                .dataModificacao(dto.getDataModificacao())
                .criadoPor(dto.getModificadoPor())
                .modificadoPor(dto.getModificadoPor())
                .build();
    }

    public static UsuarioDtoResponse fromEntityToDtoResponse(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        return UsuarioDtoResponse.builder()
                .id(usuario.getId())
                .userName(usuario.getUserName())
                .role(role)
                .build();
    }
}
