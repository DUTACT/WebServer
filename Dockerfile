FROM eclipse-temurin:17-jdk as builder
RUN apt update && apt install -y dos2unix

WORKDIR /app

# Caching gradle dependencies
COPY gradle ./gradle
COPY settings.gradle.kts ./
COPY gradlew ./
RUN chmod +x ./gradlew
COPY build.gradle.kts ./
RUN dos2unix ./gradlew

COPY src ./src
RUN ./gradlew --refresh-dependencies clean bootJar

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]