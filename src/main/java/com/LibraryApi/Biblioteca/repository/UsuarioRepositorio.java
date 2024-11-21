package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    @Query("SELECT DISTINCT u FROM Usuarios u " +
            "JOIN Emprestimos e ON u.id_usuario = e.id_usuario.id_usuario " +
            "WHERE e.devolucao = false")
    List<Usuarios> buscarUsuariosComEmprestimosPendentes();
}
