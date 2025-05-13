package com.LibraryApi.Biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livros implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Long id_livro;
    @Column(name = "titulo", nullable = false, unique = true, length = 50)
    private String titulo;
    @Column(name = "autor", nullable = false, length = 50)
    private String autor;
    @Column(name = "genero", nullable = false, length = 25)
    private String genero;
    @Column(name = "data_publicacao", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data_publicacao;
    @Column(name = "quantidade", nullable = false)
    @Min(0)
    @Max(5)
    private Integer quantidade;
}
