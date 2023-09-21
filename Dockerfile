
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/spring-boot-web.jar

# cd /opt/app
WORKDIR /opt/app

# java -jar /opt/app/app.jar
# ENTRYPOINT ["java","-jar","app.jar"]

CMD ./mvnw spring-boot:run