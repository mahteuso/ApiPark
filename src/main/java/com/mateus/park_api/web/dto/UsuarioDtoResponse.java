package com.mateus.park_api.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioDtoResponse {

    private Long id;

    private String userName;

    private String role;
}
