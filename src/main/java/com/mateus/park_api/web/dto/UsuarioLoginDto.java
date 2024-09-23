package com.mateus.park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLoginDto {

    @Email(message = "formato do e-mail está inválido", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")
    @NotBlank
    private String userName;


    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve conter 6 caracteres")
    private String password;
}
