
# API Smart Bank 🏦

## 📖 Sobre o Projeto

A **API Smart Bank** é um projeto de back-end desenvolvido para simular as operações essenciais de um sistema bancário. O objetivo principal foi aprofundar os conhecimentos em **Spring Boot**, movendo além do CRUD básico para implementar funcionalidades mais complexas e realistas.

Este projeto foi construído como um desafio de nível intermediário, focando em conceitos cruciais para o desenvolvimento de APIs robustas e seguras, como:

  - **Relacionamentos entre Entidades** com Spring Data JPA.
  - **Lógica de Negócio Transacional** para garantir a consistência dos dados em operações críticas.
  - **Segurança de Endpoints** com Spring Security.
  - **Versionamento de Banco de Dados** com Flyway, uma prática profissional essencial.

-----

## ✨ Funcionalidades Principais

  - **Gerenciamento de Clientes:**
      - [✔️] Cadastro de novos clientes com validação de dados (CPF, e-mail).
      - [✔️] Busca de clientes por ID.
  - **Gerenciamento de Contas:**
      - [✔️] Abertura de novas contas associadas a um cliente existente.
      - [✔️] Consulta de saldo e dados da conta.
  - **Operações Financeiras:**
      - [✔️] Realização de depósitos.
      - [🚧] Realização de saques com validação de saldo.
      - [🚧] Realização de transferências entre contas (operação atômica).
  - **Consultas:**
      - [✔️] Extrato de transações de uma conta com paginação.
  - **Segurança:**
      - [✔️] Todos os endpoints são protegidos, exigindo autenticação.

-----

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído utilizando o ecossistema Spring com as seguintes tecnologias:

| Ferramenta | Descrição |
| ------------------- | ------------------------------------------------------------------ |
| **Java 17** | Linguagem de programação principal. |
| **Spring Boot 3.x** | Framework principal para a construção da API. |
| **Spring Web** | Módulo para criação de endpoints RESTful. |
| **Spring Data JPA** | Para persistência de dados e abstração de consultas ao banco. |
| **Spring Security** | Para controle de autenticação e autorização. |
| **PostgreSQL** | Banco de dados relacional principal para ambiente de produção/desenvolvimento. |
| **H2 Database** | Banco de dados em memória para desenvolvimento e testes. |
| **Flyway** | Ferramenta para versionamento e migração de schema do banco de dados. |
| **Maven** | Gerenciador de dependências e build do projeto. |
| **Lombok** | Biblioteca para reduzir código boilerplate (getters, setters, etc.). |
| **JUnit 5 & Mockito** | Para a escrita de testes unitários e de integração. |

-----

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a aplicação localmente.

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

  - [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior.
  - [Apache Maven](https://maven.apache.org/download.cgi) (ou usar o Maven Wrapper incluso).
  - [Git](https://git-scm.com/downloads).
  - Um cliente de API, como [Postman](https://www.postman.com/downloads/) ou [Insomnia](https://insomnia.rest/download).

### Rodando a Aplicação

```bash
# 1. Clone o repositório
$ git clone https://github.com/[SEU USUARIO]/smart-bank-api.git

# 2. Acesse a pasta do projeto
$ cd smart-bank-api

# 3. Execute a aplicação com o Maven Wrapper
# (No Windows, use `mvnw.cmd` em vez de `./mvnw`)
$ ./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Acessando o Banco de Dados H2

Com a aplicação rodando, você pode acessar o console do banco de dados em memória:

1.  Abra o navegador e acesse: `http://localhost:8080/h2-console`
2.  Na tela de login, utilize os seguintes dados:
      - **JDBC URL:** `jdbc:h2:mem:smartbankdb`
      - **User Name:** `sa`
      - **Password:** `12345`

-----

## Endpoints da API Endpoints

Todos os endpoints requerem **Autenticação Básica (Basic Auth)**.

**Usuário padrão:** `user`
**Senha:** Gerada no console a cada inicialização da aplicação.

-----

### Clientes

| Método | Endpoint | Descrição | Corpo da Requisição (Exemplo) |
| :--- | :--- | :--- |:--- |
| `POST` | `/clientes` | Cadastra um novo cliente. | `{"nome": "Hugo Cavalcante", "cpf": "12345678900", "email": "hugo@email.com"}` |
| `GET` | `/clientes/{id}` | Busca um cliente pelo ID. | N/A |
| `POST` | `/clientes/{clienteId}/contas` | Abre uma nova conta para um cliente. | `{"agencia": "0001"}` |

-----

### Contas e Transações

| Método | Endpoint | Descrição | Corpo da Requisição (Exemplo) |
| :--- | :--- | :--- |:--- |
| `POST` | `/contas/{id}/depositar` | Realiza um depósito na conta. | `{"valor": 150.75}` |
| `GET` | `/contas/{id}/transacoes` | Lista o extrato de transações da conta. | N/A |

**Parâmetros de Paginação para `/transacoes` (Opcionais):**

  - `page`: Número da página (começa em 0).
  - `size`: Quantidade de itens por página.
  - `sort`: Campo para ordenação, ex: `sort=dataHora,desc`.

-----

## 🔐 Segurança

A segurança da API é gerenciada pelo **Spring Security**. A estratégia inicial implementada é a **HTTP Basic Authentication**, que protege todos os endpoints (exceto o console H2). As credenciais padrão são:

  - **Username:** `user`
  - **Password:** Gerada no console a cada inicialização da aplicação e deve ser copiada para uso no cliente de API.

## 🗃️ Migrations do Banco de Dados

O versionamento do schema do banco de dados é controlado pelo **Flyway**. Os scripts de migração SQL estão localizados em:
`src/main/resources/db/migration`

O Hibernate está configurado para apenas validar o schema (`ddl-auto=validate`), garantindo que as entidades JPA estejam sempre em sincronia com a estrutura definida pelo Flyway.

-----

## 👨‍💻 Autor

Projeto desenvolvido por **Hugo Vinicius** como parte de um estudo aprofundado em tecnologias de back-end.

[LinkedIn](https://www.linkedin.com/in/hugo-dcavalcante/)
