package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Livros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepositorio extends JpaRepository<Livros, Long> {
}
