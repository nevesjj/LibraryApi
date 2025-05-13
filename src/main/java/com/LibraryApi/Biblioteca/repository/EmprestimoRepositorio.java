package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmprestimoRepositorio extends JpaRepository<Emprestimos, Long> {

    long countById_usuarioAndDevolvidoFalse(Usuarios usuario);

}
