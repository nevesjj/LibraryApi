package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/emprestimos")
@Tag(name = "Empréstimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimos> criarEmprestimo(@RequestBody Emprestimos emprestimos) {
        Emprestimos emprestimoSalvo = emprestimoService.cadastrarEmprestimo(emprestimos);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimos> buscarEmprestimo(@PathVariable Long id) {
        Optional<Emprestimos> emprestimos = emprestimoService.buscarEmprestimo(id);
        return ResponseEntity.status(HttpStatus.OK).body(emprestimos.get());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Emprestimos>> buscarTodosEmprestimos() {
        List<Emprestimos> emprestimos = emprestimoService.listarTodosEmprestimos();
        return ResponseEntity.status(HttpStatus.OK).body(emprestimos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Emprestimos> atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimos emprestimo) {
        try {
            Emprestimos atualizarEmprestimo = emprestimoService.atualizarEmprestimo(id, emprestimo);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarEmprestimo);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Emprestimos> devolverLivro(@PathVariable Long id) {
        Emprestimos emprestimo = emprestimoService.devolverLivro(id);
        return ResponseEntity.status(HttpStatus.OK).body(emprestimo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
