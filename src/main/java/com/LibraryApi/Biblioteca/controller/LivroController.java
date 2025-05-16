package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.dto.LivrosDTO;
import com.LibraryApi.Biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/livros")
@Tag(name = "Livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    @Operation(description = "Realizar o cadastro de um novo livro")
    public ResponseEntity<LivrosDTO> criarLivro(@RequestBody @Valid LivrosDTO livroDTO) {
        LivrosDTO livroSalvo = livroService.cadastrarLivro(livroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @GetMapping("/{id}")
    @Operation(description = "Realiza a busca de um livro com base no seu id")
    public ResponseEntity<LivrosDTO> buscarLivro(@PathVariable Long id) {
        LivrosDTO livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/todos")
    @Operation(description = "Realiza a busca paginada de todos os livros")
    public ResponseEntity<Page<LivrosDTO>> buscarTodosLivros(Pageable pageable) {
        Page<LivrosDTO> livros = livroService.listarLivrosPaginados(pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/todos/sem-paginacao")
    @Operation(description = "Realiza a busca de todos os livros cadastrados")
    public ResponseEntity<List<LivrosDTO>> buscarTodosLivros() {
        List<LivrosDTO> livros = livroService.listarTodosLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/mais-emprestados")
    @Operation(description = "Realiza a busca dos livros com mais empréstimos")
    public ResponseEntity<List<Map<String, Object>>> buscarLivrosMaisEmprestados() {
        List<Map<String, Object>> livrosMaisEmprestados = livroService.buscarLivrosMaisEmprestados();
        return ResponseEntity.ok(livrosMaisEmprestados);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualizar as informações de um livro com base no seu id")
    public ResponseEntity<LivrosDTO> atualizarLivro(@PathVariable Long id, @RequestBody @Valid LivrosDTO livroDTO) {
        LivrosDTO atualizado = livroService.atualizarLivro(id, livroDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar o cadastro de um livro com base no seu id")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
