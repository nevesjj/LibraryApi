package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.LivroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Page<Livros> listarLivrosPaginados(Pageable pageable) {return livroRepositorio.findAll(pageable);
    }

    public List<Map<String, Object>> buscarLivrosMaisEmprestados() {
        List<Object[]> resultados = livroRepositorio.buscarLivrosMaisEmprestados();

        List<Map<String, Object>> livrosMaisEmprestados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Map<String, Object> livroInfo = new HashMap<>();
            Livros livro = (Livros) resultado[0];
            Long quantidade = (Long) resultado[1];

            livroInfo.put("livro", livro);
            livroInfo.put("quantidadeEmprestimos", quantidade);

            livrosMaisEmprestados.add(livroInfo);
        }

        return livrosMaisEmprestados;
    }
}
