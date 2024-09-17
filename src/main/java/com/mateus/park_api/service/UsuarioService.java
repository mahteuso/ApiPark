package com.mateus.park_api.service;

import com.mateus.park_api.entity.Usuario;
import com.mateus.park_api.exception.UserNameIntegrityViolationException;
import com.mateus.park_api.repository.UsuarioRepository;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;
import com.mateus.park_api.web.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDtoResponse save(UsuarioDto dto) {
        try {
            Usuario user = usuarioRepository.save(UsuarioMapper.fromDtoToEntity(dto));
            return UsuarioMapper.fromEntityToDtoResponse(user);
        } catch (org.springframework.dao.DataIntegrityViolationException dt){
            throw new UserNameIntegrityViolationException(String.format("Usuário {%s} já cadastrado", dto.getUserName()));
        }

    }

    @Transactional(readOnly = true)
    public UsuarioDtoResponse findById(Long id) {

        Usuario user = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
        return UsuarioMapper.fromEntityToDtoResponse(user);
    }

    @Transactional
    public UsuarioDtoResponse update(UsuarioDto dto, Long id) {
        Usuario user = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );

        Usuario usuario = usuarioRepository.save(UsuarioMapper.fromDtoToEntityUpdate(dto, user.getId()));
        return UsuarioMapper.fromEntityToDtoResponse(usuario);
    }

    @Transactional
    public void delete(Long id) {
        usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDtoResponse> findAll() {
        List<UsuarioDtoResponse> responseList = new ArrayList<>();
        List<Usuario> userList = usuarioRepository.findAll();

        for (Usuario user : userList) {
            UsuarioDtoResponse userDtoResponse = UsuarioMapper.fromEntityToDtoResponse(user);
            responseList.add(userDtoResponse);
        }

        return responseList;
    }

    @Transactional
    public void updateSenha(Long id, String senhaAtual, String novaSena, String confirmaSenha) {

        Usuario user = usuarioRepository.findById(id).get();
        if (!novaSena.equals(confirmaSenha)) {
            throw new RuntimeException("As senhas são doferentes");
        }

        if (!senhaAtual.equals(user.getPassword())) {
            throw new RuntimeException("Senha atual não registrada");
        }

        if (user.getPassword().equals(novaSena)) {
            throw new RuntimeException("Senha atual não pode ser igual a nova senha");
        }

        user.setPassword(novaSena);
        usuarioRepository.save(user);
    }
}
