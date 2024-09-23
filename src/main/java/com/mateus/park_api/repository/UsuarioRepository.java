package com.mateus.park_api.repository;

import com.mateus.park_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> loadUserByUsername(String userName);

    @Query("select u.role from Usuario u where u.userName like :userName")
    Usuario.Role findRoleByUsername(String userName);
}
