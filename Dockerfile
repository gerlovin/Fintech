
#FROM adoptopenjdk/openjdk11:alpine-jre
FROM eclipse-temurin:11-jre-alpine

# Установка Git
RUN apk add --no-cache git

# Refer to Maven build -> finalName
ARG JAR_FILE=target/spring-boot-web.jar
#COPY . /opt/app

# cd /opt/
WORKDIR /opt/app

COPY pom.xml mvnw  /opt/app/
COPY .mvn  /opt/app/.mvn



RUN ./mvnw dependency:go-offline

# Установка пакета ntp
#RUN apk update && apk add chrony

# Настройка синхронизации с NTP-серверами
#RUN echo "server pool.ntp.org" >> /etc/ntp.conf

# Запуск сервиса ntp
#CMD ["ntpd", "-g", "-f", "/etc/ntp.conf"]
