package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.dto.LivrosDTO;
import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import com.LibraryApi.Biblioteca.repository.LivroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

    public LivrosDTO cadastrarLivro(LivrosDTO dto) {
        Livros livro = converterParaEntidade(dto);
        Livros salvo = livroRepositorio.save(livro);
        return converterParaDTO(salvo);
    }

    public LivrosDTO buscarLivro(Long id) {
        Livros livro = livroRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro com id " + id + " não encontrado"));
        return converterParaDTO(livro);
    }

    public LivrosDTO atualizarLivro(Long id, LivrosDTO dto) {
        return livroRepositorio.findById(id).map(livro -> {
            livro.setTitulo(dto.getTitulo());
            livro.setAutor(dto.getAutor());
            livro.setGenero(dto.getGenero());
            livro.setDataPublicacao(dto.getDataPublicacao());
            livro.setQuantidade(dto.getQuantidade());
            Livros atualizado = livroRepositorio.save(livro);
            return converterParaDTO(atualizado);
        }).orElseThrow(() -> new ResourceNotFoundException("Livro com id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarLivro(Long id) {
        emprestimoRepositorio.deleteByLivroId(id);
        livroRepositorio.deleteById(id);
    }


    public List<LivrosDTO> listarTodosLivros() {
        return livroRepositorio.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Page<LivrosDTO> listarLivrosPaginados(Pageable pageable) {
        return livroRepositorio.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public List<Map<String, Object>> buscarLivrosMaisEmprestados() {
        List<Object[]> resultados = livroRepositorio.buscarLivrosMaisEmprestados();

        List<Map<String, Object>> livrosMaisEmprestados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Map<String, Object> livroInfo = new HashMap<>();
            Livros livro = (Livros) resultado[0];
            Long quantidade = (Long) resultado[1];

            livroInfo.put("livro", converterParaDTO(livro));
            livroInfo.put("quantidadeEmprestimos", quantidade);

            livrosMaisEmprestados.add(livroInfo);
        }

        return livrosMaisEmprestados;
    }

    private Livros converterParaEntidade(LivrosDTO dto) {
        Livros livro = new Livros();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setGenero(dto.getGenero());
        livro.setDataPublicacao(dto.getDataPublicacao());
        livro.setQuantidade(dto.getQuantidade());
        return livro;
    }

    private LivrosDTO converterParaDTO(Livros livro) {
        LivrosDTO dto = new LivrosDTO();
        dto.setIdLivro(livro.getIdLivro());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setGenero(livro.getGenero());
        dto.setDataPublicacao(livro.getDataPublicacao());
        dto.setQuantidade(livro.getQuantidade());
        return dto;
    }
}

