FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD test-spring-boot-jetty-1.6-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]