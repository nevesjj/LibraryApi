package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.dto.UsuariosDTO;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import com.LibraryApi.Biblioteca.repository.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

    @Transactional
    public UsuariosDTO criarUsuario(UsuariosDTO usuarioDTO) {
        Usuarios usuario = dtoToEntity(usuarioDTO);
        Usuarios salvo = usuarioRepositorio.save(usuario);
        return entityToDto(salvo);
    }

    public Optional<UsuariosDTO> buscarUsuario(Long id) {
        return usuarioRepositorio.findById(id).map(this::entityToDto);
    }

    @Transactional
    public UsuariosDTO atualizarUsuario(Long id, UsuariosDTO usuarioDTO) {
        return usuarioRepositorio.findById(id).map(usuario -> {
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setEndereco(usuarioDTO.getEndereco());
            usuario.setTelefone(usuarioDTO.getTelefone());
            Usuarios atualizado = usuarioRepositorio.save(usuario);
            return entityToDto(atualizado);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        emprestimoRepositorio.deleteByIdUsuario(id);
        usuarioRepositorio.deleteById(id);
    }

    public List<Usuarios> buscarUsuariosComEmprestimosPendentes() {
        return usuarioRepositorio.buscarUsuariosComEmprestimosPendentes();
    }

    public List<UsuariosDTO> listarTodosUsuarios() {
        return usuarioRepositorio.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Page<UsuariosDTO> listarUsuariosPaginados(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable)
                .map(this::entityToDto);
    }

    private Usuarios dtoToEntity(UsuariosDTO dto) {
        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEndereco(dto.getEndereco());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }

    private UsuariosDTO entityToDto(Usuarios usuario) {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setTelefone(usuario.getTelefone());
        dto.setEndereco(usuario.getEndereco());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}


