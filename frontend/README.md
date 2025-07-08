# Frontend Angular - Consulta de Créditos

Este projeto é o frontend Angular do desafio técnico de consulta de créditos constituídos.

## Estrutura Inicial

Gerado com [Angular CLI](https://angular.io/cli) usando:
- Routing habilitado
- SCSS para estilos

## Como iniciar

1. Instale as dependências:
   ```bash
   npm install
   ```
2. Rode o servidor de desenvolvimento:
   ```bash
   npm start
   # ou
   ng serve
   ```
3. Acesse em [http://localhost:4200](http://localhost:4200)

## Lint e Formatação

- **Lint (ESLint):**
  ```bash
  npm run lint
  ```
- **Formatação (Prettier):**
  ```bash
  npm run format
  ```

## Estrutura de Pastas

- `src/app/` - Código principal da aplicação
- `src/styles.scss` - Estilos globais
- `angular.json` - Configuração do projeto Angular

## Próximos Passos
- Implementar tela de consulta de créditos
- Adicionar integração com API REST
- Dockerizar frontend

## Docker

- **Build e execução do frontend Angular via Docker Compose:**
  ```bash
  docker-compose up frontend
  ```
- **Variáveis de ambiente:**
  - `ANGULAR_API_URL`: URL da API backend a ser consumida pelo frontend (default: http://localhost:8080)
- O frontend será servido em http://localhost:4200
- Integração automática com backend e banco via rede Docker Compose

---
> Referência: [Angular + ESLint](https://github.com/angular-eslint/angular-eslint)
