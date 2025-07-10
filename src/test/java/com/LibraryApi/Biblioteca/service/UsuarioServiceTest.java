package com.LibraryApi.Biblioteca.service;

import com.LibraryApi.Biblioteca.dto.UsuariosDTO;
import com.LibraryApi.Biblioteca.entity.Usuarios;
import com.LibraryApi.Biblioteca.repository.EmprestimoRepositorio;
import com.LibraryApi.Biblioteca.repository.UsuarioRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private EmprestimoRepositorio emprestimoRepositorio;

    @InjectMocks
    private UsuarioService usuarioService;

    @Nested
    class criarUsuario {

        @Test
        @DisplayName("Deve criar o usuário com sucesso")
        void deveCriarUsuarioComSucesso() {
            // arrange
            UsuariosDTO input = new UsuariosDTO(
                    null,
                    "joao silva",
                    "(81) 95632-1346",
                    "rua manoel",
                    "joao@email.com"
            );

            Usuarios usuarioSalvo = new Usuarios();
            usuarioSalvo.setIdUsuario(1L);
            usuarioSalvo.setNome("joao silva");
            usuarioSalvo.setTelefone("(81) 95632-1346");
            usuarioSalvo.setEndereco("rua manoel");
            usuarioSalvo.setEmail("joao@email.com");

            when(usuarioRepositorio.save(any(Usuarios.class))).thenReturn(usuarioSalvo);

            // act
            UsuariosDTO resultado = usuarioService.criarUsuario(input);

            // assert
            assertNotNull(resultado);
            assertEquals(1L, resultado.getIdUsuario());
            assertEquals("joao silva", resultado.getNome());
            assertEquals("joao@email.com", resultado.getEmail());
            verify(usuarioRepositorio).save(any(Usuarios.class));
        }

        @Test
        @DisplayName("Deve retornar o usuário quando o ID existir")
        void deveBuscarUsuarioPorIdComSucesso() {
            // arrange
            Usuarios usuario = new Usuarios();
            usuario.setIdUsuario(1L);
            usuario.setNome("joao silva");
            usuario.setTelefone("(81) 95632-1346");
            usuario.setEndereco("rua manoel");
            usuario.setEmail("joao@email.com");

            when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));

            // act
            Optional<UsuariosDTO> resultado = usuarioService.buscarUsuario(1L);

            // assert
            assertTrue(resultado.isPresent());
            assertEquals("joao silva", resultado.get().getNome());
            verify(usuarioRepositorio).findById(1L);
        }

        @Test
        @DisplayName("Deve atualizar o usuário com sucesso quando o ID existir")
        void deveAtualizarUsuarioComSucesso() {
            // arrange
            Long id = 1L;

            Usuarios existente = new Usuarios();
            existente.setIdUsuario(id);
            existente.setNome("Antigo");
            existente.setEmail("antigo@email.com");
            existente.setEndereco("Endereço antigo");
            existente.setTelefone("(81) 90000-0000");

            UsuariosDTO atualizadoDTO = new UsuariosDTO(
                    id,
                    "Novo Nome",
                    "(81) 99999-9999",
                    "Novo endereço",
                    "novo@email.com"
            );

            when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(existente));

            when(usuarioRepositorio.save(any(Usuarios.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // act
            UsuariosDTO resultado = usuarioService.atualizarUsuario(id, atualizadoDTO);

            // assert
            assertNotNull(resultado);
            assertEquals("Novo Nome", resultado.getNome());
            assertEquals("novo@email.com", resultado.getEmail());
            verify(usuarioRepositorio).findById(id);
            verify(usuarioRepositorio).save(any(Usuarios.class));
        }

        @Test
        @DisplayName("Deve deletar o usuário e seus empréstimos com sucesso")
        void deveDeletarUsuarioComSucesso() {
            // arrange
            Long id = 1L;

            // act
            usuarioService.deletarUsuario(id);

            // assert
            verify(emprestimoRepositorio).deleteByIdUsuario(id);
            verify(usuarioRepositorio).deleteById(id);
        }

        @Test
        @DisplayName("Deve retornar a lista de todos os usuários")
        void deveListarTodosUsuarios() {
            // arrange
            List<Usuarios> usuarios = List.of(
                    new Usuarios(1L, "João", "(81) 99999-0000", "Rua 1", "joao@email.com"),
                    new Usuarios(2L, "Maria", "(81) 98888-0000", "Rua 2", "maria@email.com")
            );

            when(usuarioRepositorio.findAll()).thenReturn(usuarios);

            // act
            List<UsuariosDTO> resultado = usuarioService.listarTodosUsuarios();

            // assert
            assertEquals(2, resultado.size());
            assertEquals("João", resultado.get(0).getNome());
            assertEquals("Maria", resultado.get(1).getNome());
            verify(usuarioRepositorio).findAll();
        }

        @Test
        @DisplayName("Deve retornar uma página de usuários")
        void deveListarUsuariosPaginados() {
            // arrange
            Pageable pageable = PageRequest.of(0, 2);

            List<Usuarios> lista = List.of(
                    new Usuarios(1L, "João", "(81) 99999-0000", "Rua 1", "joao@email.com"),
                    new Usuarios(2L, "Maria", "(81) 98888-0000", "Rua 2", "maria@email.com")
            );
            Page<Usuarios> pagina = new PageImpl<>(lista, pageable, lista.size());

            when(usuarioRepositorio.findAll(pageable)).thenReturn(pagina);

            // act
            Page<UsuariosDTO> resultado = usuarioService.listarUsuariosPaginados(pageable);

            // assert
            assertEquals(2, resultado.getTotalElements());
            assertEquals("Maria", resultado.getContent().get(1).getNome());
            verify(usuarioRepositorio).findAll(pageable);
        }

        @Test
        @DisplayName("Deve retornar usuários com empréstimos pendentes")
        void deveBuscarUsuariosComEmprestimosPendentes() {
            // arrange
            List<Usuarios> pendentes = List.of(
                    new Usuarios(1L, "João", "(81) 99999-0000", "Rua 1", "joao@email.com")
            );

            when(usuarioRepositorio.buscarUsuariosComEmprestimosPendentes()).thenReturn(pendentes);

            // act
            List<Usuarios> resultado = usuarioService.buscarUsuariosComEmprestimosPendentes();

            // assert
            assertEquals(1, resultado.size());
            assertEquals("João", resultado.get(0).getNome());
            verify(usuarioRepositorio).buscarUsuariosComEmprestimosPendentes();
        }
    }
}