package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.LivroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Transactional
    public Livros cadastrarLivro(Livros livro) {
        return livroRepositorio.save(livro);
    }

    public Optional<Livros> buscarLivro(Long id) {
        return livroRepositorio.findById(id);
    }

    @Transactional
    public Livros atualizarLivro(Long id, Livros livroAtualizado) {
        return livroRepositorio.findById(id).map(livros -> {
            livros.setTitulo(livroAtualizado.getTitulo());
            livros.setAutor(livroAtualizado.getAutor());
            livros.setGenero(livroAtualizado.getGenero());
            livros.setData_publicacao(livroAtualizado.getData_publicacao());
            return livroRepositorio.save(livros);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarLivro(Long id) {
        livroRepositorio.deleteById(id);
    }

    public List<Livros> listarTodosLivros() {
        return livroRepositorio.findAll();
    }
}
