package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

    @Transactional
    public Emprestimos cadastrarEmprestimo(Emprestimos emprestimo) {
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
            emprestimos.setData_emprestimo(emprestimoAtualizado.getData_emprestimo());
            emprestimos.setData_limite(emprestimoAtualizado.getData_limite());
            emprestimos.setDevolucao(emprestimoAtualizado.isDevolucao());
            return emprestimoRepositorio.save(emprestimos);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
    }

    @Transactional
    public void deletarEmprestimo(Long id) {
        emprestimoRepositorio.deleteById(id);
    }

    public List<Emprestimos> listarTodosEmprestimos() {
        return emprestimoRepositorio.findAll();
    }

}
