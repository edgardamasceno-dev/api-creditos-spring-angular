# Etapa 1: Build da aplicação Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm install --legacy-peer-deps --no-audit --progress=false
COPY . .
RUN npm run build -- --configuration=production

# Etapa 2: Servir aplicação estática com NGINX
FROM nginx:1.25-alpine AS production
WORKDIR /usr/share/nginx/html
COPY --from=build /app/dist/frontend/browser .
# Copia configuração customizada do NGINX se existir
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Permite sobrescrever variáveis de ambiente em tempo de execução
COPY ./docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"] 