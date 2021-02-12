FROM adoptopenjdk/openjdk11:alpine as builder

WORKDIR /app
COPY . /app

RUN ./gradlew build bootJar
FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=builder /app/build/libs/*.jar /server.jar

CMD ["java", "-jar", "/server.jar"]
