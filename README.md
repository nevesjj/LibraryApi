# Library API

API RESTful desenvolvida para realizar o gerenciamento de livros em uma biblioteca, permitindo o controle de usuários, livros e empréstimos.

## Funcionalidades
- **Controle de usuários**
    - Cadastrar, deletar e atualizar usuários
    - Buscar informações de todos os usuários ou de usuários específicos
    - Verificar usuários com empréstimos pendentes
    
- **Controle de livros**
    - Cadastrar, deletar e atualizar livros
    - Realizar buscas de todos os livros ou livros específicos
    - Realizar buscas dos livros mais emprestados
    - Controle de estoque de livros
    
- **Controle de empréstimos**
    - Realizar um novo empréstimo (Cada usuário pode realizar no máximo 5 empréstimos simultâneos)
    - Atualizar um empréstimo
    - Cadastrar a devolução de um livro
    
## Instalação do projeto
### Pré-requisitos
1. **Git**
    - É necessário que tenha o **git** na sua última versão
    - Verificar se o git está instalado:
    ```bash
    git --version
    ```
    - Caso não esteja instalado, é possível instalar através do [Downloads - Git](https://git-scm.com/downloads)

2. **Java 17**
    - É necessário que tenha instalado o **JDK na versão 17**
    - Verificar se o JDK está instalado:
    ```bash
    java --version
    ```
    - Caso não esteja instalado, é possível instalar através do [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17?er=221886)
    
3. **Maven**
    - É necessário que tenha instalado o **Maven**
    - Verificar se o Maven está instalado:
    ```bash
    mvn -v
    ```
    - Caso não esteja instalado, é possível instalar através do [Apache Maven Project](https://maven.apache.org/download.cgi)
    
4. **MySQL**
    - O banco de dados utilizado na aplicação é o MySQL, sendo necessário a instalação do **mysql**
    - Verificar se o MySQL está instalado:
    ```
    mysql --version
    ```
    - Caso não esteja instalado, é possível instalar através do [MySQL Community Downloads](https://dev.mysql.com/downloads/)

5. **Configuração do ambiente**
    - Porta de execução: **8080**
        - Verifique se a porta de execução já está sendo utilizada
        - Se a porta de execução já estiver sendo utilizada, após baixar a aplicação será possível alterar no arquivo `application.properties` em `server.port=8080`
    - Nome do banco de dados: **biblioteca**
    - Porta de conexão com banco de dados: 3306
        - Verifique se a porta de execução já está sendo utilizada
        - Se a porta de execução já estiver sendo utilizada, após baixar a aplicação será possível alterar no arquivo `application.properties` em `spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca`
    - Nome de usuário e senha do banco de dados:
        - O nome de usuário e senha do banco de dados foram definidos na aplicação como `root` para ambos
        - Caso o nome de usuário ou senha do seu banco de dados seja diferente, após baixar a aplicação será possível alterar no arquivo `application.properties` em `spring.datasource.username=root` e `spring.datasource.password=root`
        
### Processo de instalação e execução do projeto
1. Clonar o repositório na sua máquina
```bash
git clone git@github.com:nevesjj/LibraryApi.git
```
2. Entrar no diretório
```bash
cd LibraryApi
```
3. Se necessário, altere o ambiente conforme informado acima nos pré-requisitos
4. Executar aplicação
```bash
mvn spring-boot:run
```

## Swagger
- Com a aplicação em execução, é possível acessar o **Swagger**, onde estão documentados todos os endpoints, facilitando o entendimento do que faz cada endpoint e permitindo testá-los.  
- **URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- Se a porta de execução foi alterada, na URL deve ser passada a porta definida.


## Licença

Este projeto é dedicado ao domínio público sob a [CC0 1.0 Universal (CC0 1.0) Dedicação ao Domínio Público](https://creativecommons.org/publicdomain/zero/1.0/legalcode).

## Contribuindo

1. Faça um **fork** deste repositório.
2. Crie uma nova branch para a sua feature ou correção.
3. Faça as alterações necessárias.
4. Abra um **Pull Request** explicando as mudanças que você fez.

## Autores
- João Victor dos Santos Neves - 01577265
- Arthur Axiotes de Souza - 01595285
- Raul Gabriel Camelo Silva - 01623021
- Vinícius Júnior - 01577523
- Gabriel Vendiciano Barbosa - 01527603
- Otton Arantes dos Santos - 01595267
