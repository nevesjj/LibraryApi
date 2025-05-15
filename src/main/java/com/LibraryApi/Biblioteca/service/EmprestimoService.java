package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.dto.EmprestimoDTO;
import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.LimiteEmprestimosExcedidoException;
import com.LibraryApi.Biblioteca.exception.LivroIndisponivelException;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import com.LibraryApi.Biblioteca.repository.LivroRepositorio;
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
public class EmprestimoService {

    private EmprestimoDTO toDTO(Emprestimos emprestimo) {
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setId_emprestimo(emprestimo.getId_emprestimo());
        dto.setIdUsuario(emprestimo.getUsuario().getId_usuario());
        dto.setIdLivro(emprestimo.getLivro().getId_livro());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataLimite(emprestimo.getDataLimite());
        dto.setDevolucao(emprestimo.isDevolucao());
        return dto;
    }

    private Emprestimos toEntity(EmprestimoDTO dto, Usuarios usuario, Livros livro) {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setId_emprestimo(dto.getId_emprestimo());
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(dto.getDataEmprestimo());
        emprestimo.setDataLimite(dto.getDataLimite());
        emprestimo.setDevolucao(dto.isDevolucao());
        return emprestimo;
    }


    private static final int limite_emprestimos = 5;

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Usuarios usuario = usuarioRepositorio.findById(emprestimoDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Livros livro = livroRepositorio.findById(emprestimoDTO.getIdLivro())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        long emprestimosAtivos = emprestimoRepositorio.countEmprestimosAtivosPorUsuario(usuario);
        if (emprestimosAtivos >= limite_emprestimos) {
            throw new LimiteEmprestimosExcedidoException("Usuário já atingiu o limite de empréstimos ativos.");
        }

        if (livro.getQuantidade() <= 0) {
            throw new LivroIndisponivelException("Livro fora de estoque.");
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepositorio.save(livro);

        Emprestimos emprestimo = toEntity(emprestimoDTO, usuario, livro);
        emprestimo.setDevolucao(false);

        Emprestimos salvo = emprestimoRepositorio.save(emprestimo);
        return toDTO(salvo);
    }

    public Optional<EmprestimoDTO> buscarEmprestimo(Long id) {
        return emprestimoRepositorio.findById(id).map(this::toDTO);
    }

    @Transactional
    public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO dtoAtualizado) {
        return emprestimoRepositorio.findById(id).map(emprestimos -> {
            Usuarios usuario = usuarioRepositorio.findById(dtoAtualizado.getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            Livros livro = livroRepositorio.findById(dtoAtualizado.getIdLivro())
                    .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

            emprestimos.setUsuario(usuario);
            emprestimos.setLivro(livro);
            emprestimos.setDataEmprestimo(dtoAtualizado.getDataEmprestimo());
            emprestimos.setDataLimite(dtoAtualizado.getDataLimite());
            emprestimos.setDevolucao(dtoAtualizado.isDevolucao());

            Emprestimos atualizado = emprestimoRepositorio.save(emprestimos);
            return toDTO(atualizado);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public EmprestimoDTO devolverLivro(Long idEmprestimo) {
        Emprestimos emprestimo = emprestimoRepositorio.findById(idEmprestimo)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));
        if (emprestimo.isDevolucao()) {
            throw new RuntimeException("Este livro já foi devolvido");
        }

        emprestimo.setDevolucao(true);
        Livros livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        livroRepositorio.save(livro);

        Emprestimos salvo = emprestimoRepositorio.save(emprestimo);
        return toDTO(salvo);
    }

    @Transactional
    public void deletarEmprestimo(Long id) {
        emprestimoRepositorio.deleteById(id);
    }

    public List<EmprestimoDTO> listarTodosEmprestimos() {
        return emprestimoRepositorio.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Page<EmprestimoDTO> listarEmprestimosPaginados(Pageable pageable) {
        return emprestimoRepositorio.findAll(pageable)
                .map(this::toDTO);
    }
}

