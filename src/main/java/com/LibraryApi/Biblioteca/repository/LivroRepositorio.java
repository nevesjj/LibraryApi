package com.LibraryApi.Biblioteca.repository;

import com.LibraryApi.Biblioteca.entity.Livros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepositorio extends JpaRepository<Livros, Long> {

    @Query("SELECT l, COUNT(e) as quantidade FROM Livros l " +
            "LEFT JOIN Emprestimos e ON l.id_livro = e.id_livro.id_livro " +
            "GROUP BY l " +
            "ORDER BY quantidade DESC")
    List<Object[]> buscarLivrosMaisEmprestados();
}

