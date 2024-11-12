package com.LibraryApi.Biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private Long id_usuario;
    @NotBlank
    @Column(name = "nome_usuario", nullable = false, length = 60)
    private String nome;
    @NotBlank
    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    private String telefone;
    @NotBlank
    @Column(name = "endereco", nullable = false, length = 60)
    private String endereco;
    @NotBlank
    @Column(name = "email", nullable = false, unique = true, length = 320)
    @Email(message = "Informe um email válido")
    private String email;
}
