package com.LibraryApi.Biblioteca.entity;

import jakarta.persistence.*;
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
    private Long idLivro;

    @Column(name = "titulo", nullable = false, unique = true, length = 50)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 50)
    private String autor;

    @Column(name = "genero", nullable = false, length = 40)
    private String genero;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
}
