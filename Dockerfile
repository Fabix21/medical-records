FROM adoptopenjdk/openjdk11 as builder

WORKDIR /app
COPY . /app

RUN ./gradlew build bootJar
FROM adoptopenjdk/openjdk11:jre

COPY --from=builder /app/build/libs/*.jar /server.jar

CMD ["java", "-jar", "/server.jar"]
