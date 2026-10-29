# ---- Étape 1 : compilation avec Maven ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# On copie d'abord le wrapper Maven et le pom pour profiter du cache Docker
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

# Puis les sources, et on construit le jar
# (les tests sont déjà lancés par le job "quality-and-tests" du pipeline)
COPY src src
RUN ./mvnw -q -B -DskipTests package

# ---- Étape 2 : image d'exécution légère ----
FROM eclipse-temurin:25-jre
WORKDIR /app

# Sécurité : on crée un utilisateur sans droits (uid 10001) au lieu de tourner en root.
# Le dossier data/ (base H2) lui appartient, c'est le seul endroit où il peut écrire.
RUN groupadd --system --gid 10001 bibli \
    && useradd --system --uid 10001 --gid bibli --no-create-home bibli \
    && mkdir -p /app/data \
    && chown bibli:bibli /app/data

# Le jar appartient à root : l'appli peut le lire mais pas le modifier
COPY --from=build /app/target/*.jar app.jar

# Vérification de kubernete
USER 10001

EXPOSE 8080

# La JVM prend au plus 75 % de la mémoire donnée au conteneur
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]