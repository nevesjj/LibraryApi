package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(description = "Realiza o cadastro de um novo usuários")
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody Usuarios usuario) {
        Usuarios usuarioSalvo = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    @Operation(description = "Realiza a busca de um usuário com base no seu id")
    public ResponseEntity<Usuarios> buscarUsuario(@PathVariable Long id) {
        Optional<Usuarios> usuarios = usuarioService.buscarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarios.get());
    }

    @GetMapping("/todos")
    @Operation(description = "Realiza a busca paginada de todos os usuarios")
    public ResponseEntity<Page<Usuarios>> buscarTodosUsuarios(Pageable pageable) {
        Page<Usuarios> usuarios = usuarioService.listarUsuariosPaginados(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/todos/sem-paginacao")
    @Operation(description = "Realiza a busca de todos os usuários cadastrados")
    public ResponseEntity<List<Usuarios>> buscarTodosUsuarios() {
        List<Usuarios> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/emprestimos")
    @Operation(description = "Realiza a busca de todos os usuários com empréstimos pendenetes")
    public ResponseEntity<List<Usuarios>> buscarUsuariosComEmprestimosPendentes() {
        List<Usuarios> usuarios = usuarioService.buscarUsuariosComEmprestimosPendentes();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualizar as informações de um usuário com base no seu id")
    public ResponseEntity<Usuarios> atualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        try {
            Usuarios atualizarUsuario = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarUsuario);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar o cadastro de um livro com base no seu id")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
