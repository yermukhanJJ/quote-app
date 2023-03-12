FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8181
ADD /target/quote-0.0.1-SNAPSHOT.jar quote-backend.jar
ENTRYPOINT ["java", "-jar", "quote-backend.jar"]
