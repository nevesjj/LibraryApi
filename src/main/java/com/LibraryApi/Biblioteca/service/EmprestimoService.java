package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.entity.Emprestimos;
import com.LibraryApi.Biblioteca.entity.Livros;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.exception.LimiteEmprestimosExcedidoException;
import com.LibraryApi.Biblioteca.exception.LivroIndisponivelException;
import com.LibraryApi.Biblioteca.exception.ResourceNotFoundException;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import com.LibraryApi.Biblioteca.repository.LivroRepositorio;
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
    @Autowired
    private LivroRepositorio livroRepositorio;

    @Transactional
    public Emprestimos cadastrarEmprestimo(Emprestimos emprestimo) {
        Usuarios usuario = emprestimo.getId_usuario();
        Livros livro = emprestimo.getId_livro();

        long emprestimosAtivos = emprestimoRepositorio.countEmprestimosAtivosPorUsuario(usuario);

        if (emprestimosAtivos >= limite_emprestimos) {
            throw new LimiteEmprestimosExcedidoException("Usuario ja atingiu o limite de emprestimos ativos.");
        }

        if (livro.getQuantidade() <= 0){
            throw new LivroIndisponivelException("Livro fora de estoque.");
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepositorio.save(livro);
        emprestimo.setDevolucao(false);

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
        if (emprestimo.isDevolucao()) {
            throw new RuntimeException("Este livro ja foi devolvido");
        }

        emprestimo.setDevolucao(true);
        Livros livro = emprestimo.getId_livro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        livroRepositorio.save(livro);

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
