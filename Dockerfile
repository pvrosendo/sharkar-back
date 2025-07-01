# Estágio 1: Build - imagem completa do Maven com JDK para compilar o projeto
FROM maven:3.8.5-openjdk-17 AS build
# Define o diretório de trabalho dentro do contêiner
WORKDIR /app
# Copia os arquivos de definição do projeto primeiro para aproveitar o cache do Docker
COPY pom.xml .
# Baixa as dependências
RUN mvn dependency:go-offline
# Copia o resto do código fonte
COPY src ./src
# Compila o projeto e gera o arquivo .jar
RUN mvn package -DskipTests

# Estágio 2: Run - imagem base do Java muito menor, apenas para rodar
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copia apenas o .jar compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta que o Spring Boot usa por padrão
EXPOSE 8080
# Comando para iniciar a aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]