# book-library-api

Este projeto consiste em uma API REST para gerenciamento de um sistema de biblioteca, com cadastro de clientes e livros, e controle de retirada e devolução de exemplares

## Sumário
* [Como executar](#comoexecutar)
  * [Requisitos](#requisitos)
* [Funcionalidades](#funcionalidades)
  * [Roadmap de funcionalides](#roadmap)
* [Arquitetura do sistema](#arquitetura)


## Como executar <div id='comoexecutar'/>

O projeto pode ser executado utilizando IDE de escolha, ou através do seguinte comando na pasta raiz:

```
./gradlew bootRun
```

Para executar cenários de teste executar:

```
./gradlew test
```

#### Requisitos <div id='requisitos'/>

* Java 17+
* Banco PostgreSQL 

O apontamento para o banco pode ser ajustado no arquivo `src/main/resources/aplication.yml`

## Funcionalidades <div id='funcionalidades'/>

A API fornece as seguintes funcionalides expostas em endpoint:

* Cliente
  * Cadastrar cliente
  * Listar clientes
  * Obter dados de um cliente por CPF

* Livro
  * Cadastar livro
  * Listar livros
  * Cadastrar novo exemplar de um livro
  * Obter dados de um livro

* Retirada de livros
  * Registrar retirada de livro
  * Renovar retirada por mais duas semanas
  * Registrar devolução
  * Listar retiradas ativas

* Histórico
  * Listar histórico de retiradas de um cliente

[Documentação completa dos endpoints](/docs/endpoints.md)

#### Roadmap de funcionalidades <div id='roadmap'/>

- [x] Clientes
- [x] Livros
- [x] Retirada de livros
- [x] Histórico

## Arquitetura do sistema <div id='arquitetura'/>

A aplicação consiste em uma API desenvolvidada em Java utilizando framework spring boot, utilizando banco de dados PostgreSql

![Diagrama aplicação](/docs/diagrama-book-library-api.png)

#### Banco de dados

![Diagrama db](/docs/db-diagram.png)






