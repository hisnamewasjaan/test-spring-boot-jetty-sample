FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD [ \
  "java", \
  "-Dspring.profiles.active=default,redis,dev", \ 
  "-Dredis.host=127.0.0.1", \ 
  "-Dredis.port=6379", \ 
  "-jar", \
  "/app.jar" \
  ]
