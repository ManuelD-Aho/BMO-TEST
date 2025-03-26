# Étape 1 : Compilation de l’application avec Maven
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
# Copier le fichier pom.xml et les sources
COPY pom.xml .
COPY src/ ./src/
# Compiler et packager l’application (les tests peuvent être ignorés si non indispensables)
RUN mvn clean package -DskipTests

# Étape 2 : Création de l’image d’exécution avec OpenJDK
FROM openjdk:11-jre-slim
WORKDIR /app
# Copier le jar généré depuis l’étape de build
COPY --from=build /app/target/reunion-app-1.0-SNAPSHOT.jar reunion-app.jar
# Exposer le port utilisé par le serveur (ici 5002)
EXPOSE 5002
# Commande de démarrage
ENTRYPOINT ["java", "-jar", "reunion-app.jar"]
