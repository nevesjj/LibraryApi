package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {
}
