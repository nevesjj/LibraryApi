package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuarios criarUsuario(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Optional<Usuarios> buscarUsuario(Long id) {
        return usuarioRepositorio.findById(id);
    }

    @Transactional
    public Usuarios atualizarUsuario(Long id, Usuarios usuarioAtualizado) {
        return usuarioRepositorio.findById(id).map(usuarios -> {
            usuarios.setNome(usuarioAtualizado.getNome());
            usuarios.setEmail(usuarioAtualizado.getEmail());
            usuarios.setEndereco(usuarioAtualizado.getEndereco());
            usuarios.setTelefone(usuarioAtualizado.getTelefone());
            return usuarioRepositorio.save(usuarios);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    public List<Usuarios> listarTodosUsuarios() {
        return usuarioRepositorio.findAll();
    }
}
