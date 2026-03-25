# Estágio 1: Build da aplicação usando a imagem oficial do Maven e Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para otimizar o cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Cria a imagem final apenas com o JRE, mais leve para produção
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o jar gerado no estágio de build
COPY --from=build /app/target/*.jar app.jar

# Define a porta que a aplicação Spring Boot vai expor
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
