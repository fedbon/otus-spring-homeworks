FROM bellsoft/liberica-openjdk-alpine-musl:17
COPY target/hw16-app.jar /app/app-hw16.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app-hw16.jar"]