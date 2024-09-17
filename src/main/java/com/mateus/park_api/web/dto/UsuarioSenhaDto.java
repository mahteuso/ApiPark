package com.mateus.park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioSenhaDto {

    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve conter 6 caracteres")
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve conter 6 caracteres")
    private String novaSenha;

    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve conter 6 caracteres")
    private String confirmaSenha;
}
