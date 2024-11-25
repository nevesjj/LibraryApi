package com.LibraryApi.Biblioteca.controller;

import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody Usuarios usuario) {
        Usuarios usuarioSalvo = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarUsuario(@PathVariable Long id) {
        Optional<Usuarios> usuarios = usuarioService.buscarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarios.get());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuarios>> buscarTodosUsuarios() {
        List<Usuarios> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/emprestimos")
    public ResponseEntity<List<Usuarios>> buscarUsuariosComEmprestimosPendentes() {
        List<Usuarios> usuarios = usuarioService.buscarUsuariosComEmprestimosPendentes();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> atualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        try {
            Usuarios atualizarUsuario = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarUsuario);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
