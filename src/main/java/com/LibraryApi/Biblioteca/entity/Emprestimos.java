package com.LibraryApi.Biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livros livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_limite", nullable = false)
    private LocalDate dataLimite;

    @Column(name = "devolucao", nullable = false)
    private boolean devolucao;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    public void registrarDevolucao(LocalDate dataRetornoReal, BigDecimal valorDiarioMulta) {
        if (this.devolucao) {
            throw new IllegalStateException("Este empréstimo já foi devolvido anteriormente.");
        }

        this.devolucao = true;
        this.dataDevolucao = dataRetornoReal;

        if (dataRetornoReal.isAfter(this.dataLimite)) {
            long diasAtraso = ChronoUnit.DAYS.between(this.dataLimite, dataRetornoReal);
            this.valorMulta = valorDiarioMulta.multiply(BigDecimal.valueOf(diasAtraso));
        } else {
            this.valorMulta = BigDecimal.ZERO;
        }
    }
}