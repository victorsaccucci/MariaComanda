# -------------------------------------------------------------------
# Etapa 1: Build da aplicação Java com cache de dependências
# A imagem 'builder' será usada para compilar o código
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS builder

# Define o diretório de trabalho principal para a etapa de build
WORKDIR /app-food-manager

# Copia os arquivos essenciais para o gerenciamento de dependências do Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Concede permissão de execução ao Maven Wrapper (essencial para evitar erros)
RUN chmod +x ./mvnw

# Baixa as dependências do projeto. O Docker usará o cache desta camada
# se os arquivos pom.xml não mudarem, acelerando builds futuros.
RUN ./mvnw dependency:go-offline

# Agora, copia todo o restante do código-fonte da aplicação
COPY . .

# Compila o código, pula os testes e empacota a aplicação em um arquivo .jar
RUN ./mvnw clean package -DskipTests

# -------------------------------------------------------------------
# Etapa 2: Imagem final de execução
# Esta imagem será leve, contendo apenas o necessário para rodar a aplicação
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho final
WORKDIR /app-food-manager

# Copia apenas o artefato compilado (.jar) da etapa de build anterior
# Isso mantém a imagem final pequena e segura, sem código-fonte ou ferramentas de build.
COPY --from=builder /app-food-manager/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa por padrão
EXPOSE 8080

# Define o comando que será executado quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
