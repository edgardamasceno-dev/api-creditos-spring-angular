# API de Consulta de Créditos - Docker Setup

Este projeto contém a configuração Docker para a API RESTful de consulta de créditos constituídos, desenvolvida com Spring Boot e frontend Angular.

## 🚀 Início Rápido

### Pré-requisitos

- Docker
- Docker Compose
- Git

### Configuração Inicial

1. **Clone o repositório:**
```bash
git clone https://github.com/edgardamasceno-dev/api-creditos-spring-angular.git
cd api-creditos-spring-angular
```

2. **Configure as variáveis de ambiente:**
```bash
cp .env.example .env
```

3. **Inicie o banco de dados:**
```bash
docker-compose up postgres
```

### Variáveis de Ambiente

As principais variáveis de ambiente estão definidas no arquivo `.env`:

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `POSTGRES_DB` | Nome do banco de dados | `creditos_db` |
| `POSTGRES_USER` | Usuário do PostgreSQL | `postgres` |
| `POSTGRES_PASSWORD` | Senha do PostgreSQL | `postgres123` |
| `POSTGRES_PORT` | Porta do PostgreSQL | `5432` |

## 📁 Estrutura do Projeto

```
api-creditos-spring-angular/
├── docker-compose.yml          # Configuração do Docker Compose
├── .env                        # Variáveis de ambiente (ignorado pelo Git)
├── .env.example               # Exemplo de variáveis de ambiente
├── .gitignore                 # Arquivos ignorados pelo Git
├── database/
│   └── init/                  # Scripts de inicialização do banco
│       ├── 01-create-tables.sql
│       └── 02-populate-data.sql
├── backend/                   # Código do backend Spring Boot (futuro)
├── frontend/                  # Código do frontend Angular (futuro)
└── README.md                  # Este arquivo
```

## 🐳 Serviços Docker

### PostgreSQL
- **Imagem:** `postgres:16-alpine`
- **Porta:** `5432`
- **Banco:** `creditos_db`
- **Volumes:** Dados persistentes em `postgres_data`

### Backend (Futuro)
- **Framework:** Spring Boot
- **Porta:** `8080`
- **Dependências:** PostgreSQL

### Frontend (Futuro)
- **Framework:** Angular
- **Porta:** `4200`
- **Dependências:** Backend

## 🗄️ Banco de Dados

### Tabela: credito

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Identificador único |
| numero_credito | VARCHAR(50) | Número do crédito |
| numero_nfse | VARCHAR(50) | Número da NFS-e |
| data_constituicao | DATE | Data da constituição |
| valor_issqn | DECIMAL(15,2) | Valor do ISSQN |
| tipo_credito | VARCHAR(50) | Tipo do crédito |
| simples_nacional | BOOLEAN | Simples Nacional |
| aliquota | DECIMAL(5,2) | Alíquota aplicada |
| valor_faturado | DECIMAL(15,2) | Valor faturado |
| valor_deducao | DECIMAL(15,2) | Valor de dedução |
| base_calculo | DECIMAL(15,2) | Base de cálculo |

### Dados de Exemplo

O banco é inicializado com dados de exemplo conforme especificado no desafio técnico.

### ✅ Verificar se o Banco está Funcionando

Para testar se as tabelas foram criadas e os dados foram inseridos corretamente:

1. **Iniciar o banco de dados:**
```bash
docker-compose up postgres -d
```

2. **Verificar status do container:**
```bash
docker-compose ps
```
*O status deve aparecer como "healthy"*

3. **Verificar se a tabela foi criada:**
```bash
docker-compose exec postgres psql -U postgres -d creditos_db -c "\dt"
```
*Deve listar a tabela `credito`*

4. **Verificar quantidade de registros inseridos:**
```bash
docker-compose exec postgres psql -U postgres -d creditos_db -c "SELECT COUNT(*) as total_registros FROM credito;"
```
*Deve retornar 15 registros*

5. **Visualizar alguns dados de exemplo:**
```bash
docker-compose exec postgres psql -U postgres -d creditos_db -c "SELECT numero_credito, numero_nfse, data_constituicao, valor_issqn, tipo_credito FROM credito LIMIT 5;"
```

6. **Testar consulta específica (conforme especificação da API):**
```bash
# Consultar por número do crédito
docker-compose exec postgres psql -U postgres -d creditos_db -c "SELECT * FROM credito WHERE numero_credito = '123456';"

# Consultar por número da NFS-e
docker-compose exec postgres psql -U postgres -d creditos_db -c "SELECT * FROM credito WHERE numero_nfse = '7891011';"
```

7. **Parar o banco (quando necessário):**
```bash
docker-compose down
```

#### 🎯 Resultado Esperado

Se tudo estiver funcionando corretamente, você deve ver:
- ✅ Container com status "healthy"
- ✅ Tabela `credito` criada com 11 colunas + created_at/updated_at
- ✅ 15 registros inseridos
- ✅ Dados conforme especificado no desafio técnico
- ✅ Consultas funcionando corretamente

## 📝 Comandos Úteis

### Docker Compose

```bash
# Iniciar apenas o PostgreSQL
docker-compose up postgres

# Iniciar todos os serviços em background
docker-compose up -d

# Parar todos os serviços
docker-compose down

# Visualizar logs
docker-compose logs -f postgres

# Acessar o banco de dados
docker-compose exec postgres psql -U postgres -d creditos_db
```

### Limpeza

```bash
# Parar e remover containers, redes e volumes
docker-compose down -v

# Remover imagens não utilizadas
docker image prune
```

## 🔧 Desenvolvimento

Este projeto está estruturado para suportar:

- **Backend:** Spring Boot com JPA/Hibernate
- **Frontend:** Angular 2+
- **Banco de Dados:** PostgreSQL
- **Containerização:** Docker
- **Testes:** JUnit, Mockito
- **Mensageria:** Kafka/Azure Service Bus (futuro)

## 🛡️ Segurança

- Arquivo `.env` está no `.gitignore`
- Senhas devem ser alteradas em produção
- Use variáveis de ambiente para configurações sensíveis

## 📋 Próximos Passos

1. [ ] Implementar backend Spring Boot
2. [ ] Implementar frontend Angular
3. [ ] Adicionar testes automatizados
4. [ ] Configurar CI/CD
5. [ ] Adicionar mensageria (Kafka/Azure Service Bus)
6. [ ] Implementar monitoramento

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

---

> Este projeto segue os padrões de desenvolvimento descritos em [api-creditos-spring-angular.md](./api-creditos-spring-angular.md). 