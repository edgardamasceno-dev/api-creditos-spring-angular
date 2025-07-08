# Frontend Angular - Consulta de Créditos

Este projeto é o frontend Angular do desafio técnico de consulta de créditos constituídos.

## Requisitos

- Node.js 18+ (recomendado Node 20)
- npm 9+
- Docker e Docker Compose (opcional para ambiente containerizado)

## Instalação

1. Acesse a pasta `frontend`:
   ```bash
   cd frontend
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```

## Execução em Desenvolvimento

1. Inicie o servidor de desenvolvimento:
   ```bash
   npm start
   # ou
   ng serve
   ```
2. Acesse a aplicação em [http://localhost:4200](http://localhost:4200)

> Por padrão, o frontend espera que o backend esteja rodando em http://localhost:8080.

## Testes

- **Testes unitários (Jest):**
  ```bash
  npm run test
  ```
- **Testes E2E (Playwright):**
  ```bash
  npm run e2e
  ```

## Lint e Formatação

- **Lint (ESLint):**
  ```bash
  npm run lint
  ```
- **Formatação (Prettier):**
  ```bash
  npm run format
  ```

## Build de Produção

Para gerar o build otimizado para produção:
```bash
npm run build
```
Os arquivos finais estarão em `dist/frontend/browser`.

## Docker

### Build e execução via Docker Compose

1. No diretório raiz do projeto, execute:
   ```bash
   docker-compose up frontend
   ```
2. O frontend será servido em [http://localhost:4200](http://localhost:4200)

### Variáveis de ambiente
- `ANGULAR_API_URL`: URL da API backend a ser consumida pelo frontend (default: http://localhost:8080)

### Build manual da imagem
```bash
cd frontend
docker build -t frontend-angular .
```

## Estrutura de Pastas

- `src/app/` - Código principal da aplicação (componentes, serviços)
- `src/styles.scss` - Estilos globais
- `angular.json` - Configuração do projeto Angular
- `Dockerfile` - Build e deploy containerizado
- `nginx.conf` - Configuração do NGINX para produção
- `docker-entrypoint.sh` - Script de inicialização para variáveis de ambiente

## Exemplo de Uso

- Buscar créditos por número da NFS-e ou número do crédito
- Visualizar detalhes completos em modal
- Interface responsiva e acessível

## Observações

- O frontend está preparado para integração com backend e banco via Docker Compose
- Para ambientes diferentes, ajuste a variável `ANGULAR_API_URL` conforme necessário
- Documentação do backend: consulte a pasta `/backend` e o README principal do projeto

---
> Referência: [Angular + ESLint](https://github.com/angular-eslint/angular-eslint)
> 
> Para dúvidas sobre o desafio, consulte o arquivo `api-creditos-spring-angular.md` na raiz do projeto.
