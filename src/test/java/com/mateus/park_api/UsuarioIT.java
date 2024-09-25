package com.mateus.park_api;

import com.mateus.park_api.web.dto.UsuarioDto;
import com.mateus.park_api.web.dto.UsuarioDtoResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_withValidUserNameAndPassword_ReturnUserCreatedWithStatus201(){
        UsuarioDtoResponse responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioDto("malu@email.com", "987656"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioDtoResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUserName()).isEqualTo("maluco@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

    }
}
