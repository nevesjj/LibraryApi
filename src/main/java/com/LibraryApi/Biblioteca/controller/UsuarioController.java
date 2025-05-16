package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.dto.UsuariosDTO;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.UsuarioService;
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
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(description = "Realiza o cadastro de um novo usuário")
    public ResponseEntity<UsuariosDTO> criarUsuario(@Valid @RequestBody UsuariosDTO usuarioDTO) {
        UsuariosDTO usuarioSalvo = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    @Operation(description = "Realiza a busca de um usuário com base no seu id")
    public ResponseEntity<UsuariosDTO> buscarUsuario(@PathVariable Long id) {
        Optional<UsuariosDTO> usuarioDTO = usuarioService.buscarUsuario(id);
        return usuarioDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    @Operation(description = "Realiza a busca paginada de todos os usuários")
    public ResponseEntity<Page<UsuariosDTO>> buscarTodosUsuarios(Pageable pageable) {
        Page<UsuariosDTO> usuarios = usuarioService.listarUsuariosPaginados(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/todos/sem-paginacao")
    @Operation(description = "Realiza a busca de todos os usuários cadastrados")
    public ResponseEntity<List<UsuariosDTO>> buscarTodosUsuarios() {
        List<UsuariosDTO> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/emprestimos")
    @Operation(description = "Realiza a busca de todos os usuários com empréstimos pendentes")
    public ResponseEntity<List<Usuarios>> buscarUsuariosComEmprestimosPendentes() {
        List<Usuarios> usuarios = usuarioService.buscarUsuariosComEmprestimosPendentes();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Operation(description = "Atualizar as informações de um usuário com base no seu id")
    public ResponseEntity<UsuariosDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuariosDTO usuarioDTO) {
        try {
            UsuariosDTO atualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
            return ResponseEntity.ok(atualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar o cadastro de um usuário com base no seu id")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}