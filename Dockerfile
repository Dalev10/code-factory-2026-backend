# ETAPA 1: Compilación (Build)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
# Copiamos el wrapper de Maven y el pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# Damos permisos de ejecución al wrapper
RUN chmod +x ./mvnw
# Descargamos las dependencias (esto se guarda en caché para ser más rápido)
RUN ./mvnw dependency:go-offline
# Copiamos el código fuente y compilamos el ejecutable (.jar)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# ETAPA 2: Ejecución (Run)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Solo copiamos el .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar
# Exponemos el puerto de Spring Boot
EXPOSE 8080
# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]