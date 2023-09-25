
#FROM adoptopenjdk/openjdk11:alpine-jre
FROM eclipse-temurin:11-jre-alpine


# Refer to Maven build -> finalName
ARG JAR_FILE=target/spring-boot-web.jar


# cd /opt/app
WORKDIR /opt/app

COPY pom.xml mvnw  /opt/app/
COPY .mvn  /opt/app/.mvn


# java -jar /opt/app/app.jar
# ENTRYPOINT ["java","-jar","app.jar"]
RUN ./mvnw dependency:go-offline
#RUN mvn package -DskipTests



# Используйте официальный образ Maven
#FROM maven:3.6.3-jdk-11

# Установите рабочую директорию внутри контейнера
#WORKDIR /app

# Копируйте файлы с зависимостями Maven и файлы проекта
#COPY pom.xml /app/
#COPY src /app/src/

# Выполните сборку проекта
#RUN mvn clean install

# Команда для запуска приложения (замените её на вашу)
#CMD ["java", "-jar", "target/your-app.jar"]