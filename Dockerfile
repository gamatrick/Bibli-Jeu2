
# Étape 1 : compilation de l'application avec Maven et Java 25
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copier la configuration Maven
COPY pom.xml .

# Copier le code source
COPY src ./src

# Compiler l'application sans lancer les tests
RUN mvn -B -DskipTests package

# Étape 2 : image finale pour exécuter l'application
FROM eclipse-temurin:25-jre

WORKDIR /app

# Récupérer le JAR généré lors de la compilation
COPY --from=build /app/target/*.jar app.jar

# Port utilisé par défaut par Spring Boot
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]