[![Build Status](https://github.com/edgardamasceno-dev/api-creditos-spring-angular/actions/workflows/backend-ci.yml/badge.svg)](https://github.com/edgardamasceno-dev/api-creditos-spring-angular/actions/workflows/backend-ci.yml)

## Documentação da API

Acesse a documentação interativa dos endpoints:
- **Swagger UI:** http://localhost:8080/swagger
- **Redoc (open source):** http://localhost:8080/docs

A documentação é gerada automaticamente via [springdoc-openapi](https://springdoc.org/). O acesso sem ".html" é feito via redirecionamento elegante.

## Arquitetura Backend

- **Camadas:**
  - Controller: expõe endpoints REST
  - Service: lógica de negócio
  - Repository: acesso a dados (JPA)
  - DTO/Mapper: transporte e conversão de dados
- **Padrões:**
  - Domain-Driven Design (DDD)
  - Test-Driven Development (TDD)
  - Design Patterns (ex: Service, Repository)
- **Documentação:**
  - OpenAPI 3.0, Swagger UI e Redoc
  - Anotações OpenAPI nos controllers e DTOs 

## Integração Contínua, Build, Lint e Testes

[![Build Status](https://github.com/edgardamasceno-dev/api-creditos-spring-angular/actions/workflows/backend-ci.yml/badge.svg)](https://github.com/edgardamasceno-dev/api-creditos-spring-angular/actions/workflows/backend-ci.yml)

Este projeto utiliza **GitHub Actions** para garantir a qualidade do código do backend:

- **Build automatizado:** executado a cada push/pull request.
- **Testes automatizados:** todos os testes JUnit são executados no CI.
- **Lint/Checkstyle:** validação automática do padrão Google para Java.
- **Badge de build:** indica o status da última execução do CI.

### Comandos Locais

- **Build e testes:**
  ```bash
  ./mvnw clean verify
  ```
- **Apenas checkstyle:**
  ```bash
  ./mvnw checkstyle:check
  ```
- **Gerar relatório de cobertura:**
  ```bash
  ./mvnw jacoco:report
  ```

### Configuração do Checkstyle

- O padrão utilizado é o **Google Java Style** (`google_checks.xml`).
- O checkstyle é executado automaticamente no CI e localmente via Maven.
- Para customizar regras, edite o arquivo `backend/google_checks.xml`.

### Workflow CI

O workflow está em `.github/workflows/backend-ci.yml` e executa:
- Checkout do código
- Configuração do JDK 17
- Build, testes e checkstyle 