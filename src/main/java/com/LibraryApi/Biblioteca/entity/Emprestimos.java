package com.LibraryApi.Biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "emprestimos")
public class Emprestimos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private Long idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false, referencedColumnName = "id_usuario")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false, referencedColumnName = "id_livro")
    private Livros livro;

    @Column(name = "data_emprestimo", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEmprestimo;

    @Column(name = "data_limite", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataLimite;

    @Column(name = "devolucao", nullable = false)
    private boolean devolucao;
}
