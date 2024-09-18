package com.mateus.park_api.web.controller;

import com.mateus.park_api.service.UsuarioService;
import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;
import com.mateus.park_api.web.dto.UsuarioSenhaDto;
import com.mateus.park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações de CRUD de um usuário.")
@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso inválido por dados de entrada inválidos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> create(@Valid @RequestBody UsuarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
    }


    @Operation(summary = "Recuperar um usuário pelo id", description = "Recurso para recuperar um usuário pelo id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }


    @Operation(summary = "Modificar dados do usuário", description = "Recurso para modificar os dados de um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resurso modificado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class))),
                    @ApiResponse(responseCode = "404", description = "REcurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> update(@Valid @RequestBody UsuarioDto dto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(dto, id));
    }


    @Operation(summary = "Deletar usuário", description = "Recurso para deletar um usuário do sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso deletado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @Operation(summary = "Mostrar todos os usuários", description = "Recurso para retornar os dados de todos os usuários.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDtoResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @Operation(summary = "Atualizar senha", description = "Recurso para atualizar a senha de um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha modificada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSenha(@PathVariable("id") Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        usuarioService.updateSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
}
