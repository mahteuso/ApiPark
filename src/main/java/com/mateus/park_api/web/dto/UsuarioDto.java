package com.mateus.park_api.web.dto;


import com.mateus.park_api.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioDto {

    private Long id;

    @Email(message = "Formato do email invalido", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")
    @NotBlank
    private String userName;

    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve conter 6 caracteres")
    private String password;

    private Usuario.Role role;

    private LocalDate dataCriacao;

    private LocalDate dataModificacao;

    private String criadoPor;

    private String modificadoPor;

    public UsuarioDto(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
