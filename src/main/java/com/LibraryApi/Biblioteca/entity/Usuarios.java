package com.LibraryApi.Biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nome_usuario", nullable = false, length = 60)
    private String nome;

    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    private String telefone;

    @Column(name = "endereco", nullable = false, length = 60)
    private String endereco;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
}
