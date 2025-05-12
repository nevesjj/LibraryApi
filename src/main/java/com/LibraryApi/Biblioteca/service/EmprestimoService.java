package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.LimiteEmprestimosExcedidoException;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private static final int limite_emprestimos = 5;

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;


    @Transactional
    public Emprestimos cadastrarEmprestimo(Emprestimos emprestimo) {
        Usuarios usuario = emprestimo.getId_usuario();

        long emprestimosAtivos = emprestimoRepositorio.countById_usuario(usuario);

        if (emprestimosAtivos >= limite_emprestimos) {
            throw new LimiteEmprestimosExcedidoException("Usuario ja atingiu o limite de emprestimos ativos.");
        }
        return emprestimoRepositorio.save(emprestimo);
    }

    public Optional<Emprestimos> buscarEmprestimo(Long id) {
        return emprestimoRepositorio.findById(id);
    }

    @Transactional
    public Emprestimos atualizarEmprestimo(Long id, Emprestimos emprestimoAtualizado) {
        return emprestimoRepositorio.findById(id).map(emprestimos -> {
            emprestimos.setId_usuario(emprestimoAtualizado.getId_usuario());
            emprestimos.setId_livro(emprestimoAtualizado.getId_livro());
            emprestimos.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
            emprestimos.setDataLimite(emprestimoAtualizado.getDataLimite());
            emprestimos.setDevolucao(emprestimoAtualizado.isDevolucao());
            return emprestimoRepositorio.save(emprestimos);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public Emprestimos devolverLivro(Long idEmprestimo) {
        Emprestimos emprestimo = emprestimoRepositorio.findById(idEmprestimo)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        if (!emprestimo.isDevolucao()) {
            emprestimo.setDevolucao(true);
        }

        return emprestimoRepositorio.save(emprestimo);
    }

    @Transactional
    public void deletarEmprestimo(Long id) {
        emprestimoRepositorio.deleteById(id);
    }

    public List<Emprestimos> listarTodosEmprestimos() {
        return emprestimoRepositorio.findAll();
    }

    public Page<Emprestimos> listarEmprestimosPaginados(Pageable pageable) {
        return emprestimoRepositorio.findAll(pageable);
    }


}
