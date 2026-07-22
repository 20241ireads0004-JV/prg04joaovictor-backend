# ==========================================
# ETAPA 1: Compilacao da aplicacao (Build)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretorio de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de dependencias do Maven (pom.xml)
COPY pom.xml .

# Baixa as dependencias do projeto (melhora o tempo de build futuro)
RUN mvn dependency:go-offline

# Copia todo o codigo-fonte do projeto para dentro do container
COPY src ./src

# Compila o projeto e gera o arquivo .jar (ignorando os testes no build)
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Execucao da aplicacao (Runtime)
# ==========================================
FROM eclipse-temurin:21-jre-alpine

# Define o diretorio de trabalho para a imagem final
WORKDIR /app

# Copia apenas o arquivo .jar gerado na Etapa 1
COPY --from=build /app/target/*.jar app.jar

# Expoe a porta 8080 (mesma porta configurada no Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicacao Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]