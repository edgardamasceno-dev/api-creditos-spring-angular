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