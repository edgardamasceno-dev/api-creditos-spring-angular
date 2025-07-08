#!/bin/sh
# Substitui variáveis de ambiente no arquivo main.js (ou equivalente) para API_URL
if [ -n "$ANGULAR_API_URL" ]; then
  find /usr/share/nginx/html -type f -name '*.js' -exec sed -i "s|API_URL_PLACEHOLDER|$ANGULAR_API_URL|g" {} +
fi
exec "$@" 