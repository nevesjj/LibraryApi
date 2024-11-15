package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepositorio extends JpaRepository<Emprestimos, Long> {
}
