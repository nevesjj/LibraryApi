package com.LibraryApi.Biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuariosDTO {

    private Long idUsuario;

    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    private String telefone;

    @NotNull(message = "O endereço é obrigatório")
    private String endereco;

    @NotNull(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;
}
