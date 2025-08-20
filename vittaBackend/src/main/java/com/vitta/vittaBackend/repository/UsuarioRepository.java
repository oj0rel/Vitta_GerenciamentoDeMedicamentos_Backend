package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
