FROM bellsoft/liberica-openjdk-alpine-musl:17
COPY target/hw17-app.jar /app/app-hw17.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app-hw17.jar"]