FROM adoptopenjdk/openjdk11:alpine as builder

WORKDIR /app
COPY . /app

RUN ./gradlew build bootJar
FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=builder /app/build/libs/*.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
