package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livros> criarLivro(@RequestBody Livros livro) {
        Livros livroSalvo = livroService.cadastrarLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livros> buscarLivro(@PathVariable Long id) {
        Optional<Livros> livros = livroService.buscarLivro(id);
        return ResponseEntity.status(HttpStatus.OK).body(livros.get());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Livros>> buscarTodosLivros() {
        List<Livros> livros = livroService.listarTodosLivros();
        return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @GetMapping("/mais-emprestados")
    public ResponseEntity<List<Map<String, Object>>> buscarLivrosMaisEmprestados() {
        List<Map<String, Object>> livrosMaisEmprestados = livroService.buscarLivrosMaisEmprestados();
        return ResponseEntity.status(HttpStatus.OK).body(livrosMaisEmprestados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livros> atualizarLivro(@PathVariable Long id, @RequestBody Livros livro) {
        try {
            Livros atualizarLivro = livroService.atualizarLivro(id, livro);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarLivro);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
