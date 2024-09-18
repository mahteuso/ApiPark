package com.mateus.park_api.service.serviceimpl;

import com.mateus.park_api.entity.Usuario;
import com.mateus.park_api.exception.NotFoundClientId;
import com.mateus.park_api.exception.PasswordInvalidException;
import com.mateus.park_api.exception.UserNameIntegrityViolationException;
import com.mateus.park_api.repository.UsuarioRepository;
import com.mateus.park_api.service.UsuarioService;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;
import com.mateus.park_api.web.exception.BadRequestDeleteUser;
import com.mateus.park_api.web.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDtoResponse save(UsuarioDto dto) {
        try {
            Usuario user = usuarioRepository.save(UsuarioMapper.fromDtoToEntity(dto));
            return UsuarioMapper.fromEntityToDtoResponse(user);
        } catch (org.springframework.dao.DataIntegrityViolationException dt) {
            throw new UserNameIntegrityViolationException(String.format("Usuário {%s} já cadastrado", dto.getUserName()));
        }

    }

    @Transactional(readOnly = true)
    public UsuarioDtoResponse findById(Long id) {
        try {
            Usuario user = usuarioRepository.findById(id).get();
            return UsuarioMapper.fromEntityToDtoResponse(user);
        } catch (java.lang.RuntimeException nf) {
            throw new NotFoundClientId(String.format("Usuário id={%s} não encontrado", id));
        }
    }

    @Transactional
    public UsuarioDtoResponse update(UsuarioDto dto, Long id) {
        try {
            Usuario user = usuarioRepository.findById(id).get();
            Usuario usuario = usuarioRepository.save(UsuarioMapper.fromDtoToEntityUpdate(dto, user.getId()));
            return UsuarioMapper.fromEntityToDtoResponse(usuario);
        } catch (java.lang.RuntimeException nf) {
            throw new NotFoundClientId(String.format("Usuário id={%s} não encontrado", id));
        }
    }

    @Transactional
    public void delete(Long id) {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new BadRequestDeleteUser(String.format("Usuário id={%s} não existe.", id));
        }
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
            throw new PasswordInvalidException(String.format("As senhas são diferentes {%s} != {%s}", novaSena, confirmaSenha));
        }

        if (!senhaAtual.equals(user.getPassword())) {
            throw new PasswordInvalidException("Senha atual não registrada");
        }

        if (user.getPassword().equals(novaSena)) {
            throw new PasswordInvalidException("Senha atual não pode ser igual a nova senha");
        }

        user.setPassword(novaSena);
        usuarioRepository.save(user);
    }

}
