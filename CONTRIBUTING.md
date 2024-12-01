# Library API

O **Library API** é uma API RESTful para gerenciar bibliotecas. O sistema permite o controle de usuários, livros e empréstimos, com funcionalidades como cadastro, busca, atualização e gerenciamento de devoluções e pendências.

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

## Como contribuir

1. Faça um **fork** do repositório: Clique no botão "Fork" no canto superior direito da página do GitHub.

2. Clone o seu fork localmente:
```bash
git clone git@github.com:SEU_USUARIO/LibraryApi.git
```

3. Crie uma nova branch para sua funcionalidade ou correção:
```bash
git checkout -b minha-feature
```

4. Implemente sua contribuição.
Certifique-se de seguir os padrões de código e adicionar testes, se necessário.

5.Commit suas alterações:
```bash
git add .
git commit -m "Descrição da minha alteração"
```

6. Envie sua branch para o repositório remoto:
```bash
git push origin minha-feature
```

7. Abra um Pull Request:
Vá até o repositório original no GitHub e clique em "**New Pull Request**". Explique detalhadamente suas alterações.

## Observações
- Use mensagens de commit claras e objetivas.
- Descreva o que foi feito e motivo das alterações no Pull Request.
- Verifique se suas alterações não impactou negativamente no resto da aplicação.
