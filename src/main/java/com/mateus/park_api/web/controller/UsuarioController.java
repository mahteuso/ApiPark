package com.mateus.park_api.web.controller;

import com.mateus.park_api.service.UsuarioService;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;
import com.mateus.park_api.web.dto.UsuarioSenhaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> create(@Valid @RequestBody UsuarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> update(@Valid @RequestBody UsuarioDto dto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDtoResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSenha(@PathVariable("id") Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        usuarioService.updateSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
}
