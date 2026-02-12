# Contadora API

## Introdução

A **Contadora API** é uma aplicação backend para **gestão financeira e controle de produtos de uma loja**. Ela permite cadastrar produtos, controlar estoque, registrar vendas e calcular lucros, servindo como base para sistemas de controle comercial.

Este projeto foi desenvolvido com foco em aprendizado prático de **Spring Boot**, **API REST** e **boas práticas de backend**.

---

## Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **MySQL 8**
* **Flyway** (versionamento do banco de dados)
* **Docker & Docker Compose**
* **Lombok**
* **Maven**

---

## Começando

### Pré-requisitos

Antes de iniciar, você precisa ter instalado:

* **Java 17**
* **Docker**
* **Docker Compose**
* **Git**

---

## Instalação e Execução

### 1️Clonar o repositório

```bash
git clone https://github.com/gustavomazur/Controle-venda.git
cd contadora-api
```

### 2️Subir o banco de dados com Docker

```bash
docker compose up -d
```

Isso irá subir um container MySQL configurado para o projeto.

### 3️Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## Banco de Dados e Migrations

O projeto utiliza **Flyway** para versionamento do banco.

As migrations ficam em:

```
src/main/resources/db/migration
```

Exemplo:

```
V1__create_table_produto.sql
```

As migrations são executadas automaticamente ao iniciar a aplicação.

---

## Endpoints da API

### Produto

#### Criar produto

```
POST /produto
```

#### Listar produtos

```
GET /produto
```

#### Atualizar produto

```
PUT /produto
```

#### Deletar produto

```
DELETE /produto/{id}
```

#### Calcular lucro total

```
GET /produto/lucro-total
```

#### Vender produto

```
POST /produto/{id}/vender/{quantidade}
```

---

## Estrutura do Projeto

```text
src/main/java
├── controller
│   └── ProdutoController.java
├── model
│   └── Produto.java
├── DTO
│   ├── DadosCadastrarProdutoDTO.java
│   └── DadosAtualizarProdutoDTO.java
└── br/com/contadora/contadora_api
    └── ContadoraApiApplication.java
```

**Observação importante**

Atualmente, os pacotes `controller`, `model` e `DTO` estão diretamente na raiz de `src/main/java`, enquanto a classe principal da aplicação está em `br.com.contadora.contadora_api`.

Isso **pode causar problemas de component scan no Spring Boot**. O ideal, futuramente, é mover todos os pacotes para dentro do mesmo pacote base (`br.com.contadora.contadora_api`).

---

## Verificação Manual

Após subir o projeto:

* Acesse os endpoints via **Postman**, **Insomnia** ou navegador
* Verifique se o banco foi criado corretamente
* Confirme no log que o Flyway executou as migrations

---

## Observações Finais

Este projeto tem foco educacional, mas segue práticas usadas no mercado, como:

* Versionamento de banco
* API REST organizada
* Separação de responsabilidades

Evoluções futuras podem incluir:

* Autenticação
* Paginação
* Validações mais robustas
* Refatoração de estrutura de pacotes

---

Projeto em desenvolvimento para aprendizado e evolução contínua.
