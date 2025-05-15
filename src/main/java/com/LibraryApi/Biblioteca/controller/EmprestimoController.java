package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.dto.EmprestimoDTO;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/emprestimos")
@Tag(name = "Empréstimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    @Operation(description = "Realizar o cadastro de um novo empréstimo, cada usuário pode ter no máximo 5 empréstimos")
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO dtoSalvo = emprestimoService.cadastrarEmprestimo(emprestimoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
    }

    @GetMapping("/{id}")
    @Operation(description = "Realiza a busca de um empréstimo com base no seu id")
    public ResponseEntity<EmprestimoDTO> buscarEmprestimo(@PathVariable Long id) {
        EmprestimoDTO dto = emprestimoService.buscarEmprestimo(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/todos")
    @Operation(description = "Realiza a busca paginada de todos os empréstimos")
    public ResponseEntity<Page<EmprestimoDTO>> buscarTodosEmprestimos(Pageable pageable) {
        Page<EmprestimoDTO> pageDTO = emprestimoService.listarEmprestimosPaginados(pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/todos/sem-paginacao")
    @Operation(description = "Realiza a busca de todos os empréstimos")
    public ResponseEntity<List<EmprestimoDTO>> buscarTodosEmprestimos() {
        List<EmprestimoDTO> listaDTO = emprestimoService.listarTodosEmprestimos();
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualizar as informações de um empréstimo com base no seu id")
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO dtoAtualizado = emprestimoService.atualizarEmprestimo(id, emprestimoDTO);
        return ResponseEntity.ok(dtoAtualizado);
    }

    @PatchMapping("/{id}/devolver")
    @Operation(description = "Realizar a devolução de um livro")
    public ResponseEntity<EmprestimoDTO> devolverLivro(@PathVariable Long id) {
        EmprestimoDTO dto = emprestimoService.devolverLivro(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar um empréstimo com base no seu id")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
