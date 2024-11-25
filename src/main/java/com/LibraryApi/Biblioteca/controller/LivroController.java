package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    @Operation(description = "Realizar o cadastro de um novo livro")
    public ResponseEntity<Livros> criarLivro(@RequestBody Livros livro) {
        Livros livroSalvo = livroService.cadastrarLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @GetMapping("/{id}")
    @Operation(description = "Realiza a busca de um livro com base no seu id")
    public ResponseEntity<Livros> buscarLivro(@PathVariable Long id) {
        Optional<Livros> livros = livroService.buscarLivro(id);
        return ResponseEntity.status(HttpStatus.OK).body(livros.get());
    }

    @GetMapping("/todos")
    @Operation(description = "Realiza a busca de todos os livros cadastrados")
    public ResponseEntity<List<Livros>> buscarTodosLivros() {
        List<Livros> livros = livroService.listarTodosLivros();
        return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @GetMapping("/mais-emprestados")
    @Operation(description = "Realiza a busca dos livros com mais empréstimos")
    public ResponseEntity<List<Map<String, Object>>> buscarLivrosMaisEmprestados() {
        List<Map<String, Object>> livrosMaisEmprestados = livroService.buscarLivrosMaisEmprestados();
        return ResponseEntity.status(HttpStatus.OK).body(livrosMaisEmprestados);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualizar as informações de um livro com base no seu id")
    public ResponseEntity<Livros> atualizarLivro(@PathVariable Long id, @RequestBody Livros livro) {
        try {
            Livros atualizarLivro = livroService.atualizarLivro(id, livro);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarLivro);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar o cadastro de um livro com base no seu id")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
