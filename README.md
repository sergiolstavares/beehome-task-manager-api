# BeehomeTaskManagerApi

API desenvolvida utilizando **Java Spring Boot**. Este projeto oferece funcionalidades para gerenciamento de tarefas, autenticação de usuários via JWT, persistência de dados no banco de dados PostgreSQL e documentação interativa com Swagger.

---

## Aplicação em Produção

A API está funcionando em produção e pode ser acessada pelo seguinte link:

- **Aplicação funcional em produção**: [https://beehome-task-manager-ui.vercel.app/](https://beehome-task-manager-ui.vercel.app/)
- **Swagger UI**: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

---

## Requisitos

Certifique-se de ter os seguintes requisitos antes de executar o projeto localmente:

- **Java 17 ou superior**
- **Gradle**
- **PostgreSQL**

---

## Configuração do Banco de Dados

1. Certifique-se de que o PostgreSQL está instalado e rodando.
2. Crie o banco de dados necessário:
```sql
CREATE DATABASE beehome;

CREATE SCHEMA register;

CREATE TABLE register."user" (
	id uuid primary key NOT NULL,
	username varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL
);

CREATE TABLE register.task(
	id uuid primary key not null,
	title varchar not null,
	description varchar not null,
	status varchar not null,
	created_on timestamp not null default now(),
	deadline timestamp,
	assigned_to uuid not null references register."user"(id)
);
