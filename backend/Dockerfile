# syntax=docker/dockerfile:1
# Etapa 1: Build do JAR usando Maven com cache eficiente
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests \
  && ls -lh target

# Etapa 2: Imagem final minimalista
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
# Copia apenas o JAR correto (exclui .original)
COPY --from=build /build/target/creditos-0.0.1-SNAPSHOT.jar app.jar
# Permissões (caso rode como non-root)
RUN chmod 644 app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
# Copia o script wait-for-it para o container
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["sh", "-c", "/wait-for-it.sh $POSTGRES_HOST:$POSTGRES_PORT -- java $JAVA_OPTS -jar app.jar"]

# Observação: Este Dockerfile é exclusivo do backend. O frontend terá seu próprio Dockerfile futuramente. 