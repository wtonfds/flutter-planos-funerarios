FROM maven:3.6.1-jdk-11-slim  AS build

COPY . /usr/src/
COPY pom.xml /usr/src/
COPY Dockerfile /usr/src/
RUN mvn -f /usr/src/pom.xml clean install -Dmaven.test.skip=true


FROM openjdk:11.0.6-jre

ENV SERVER_PORT=8080
ENV LOGGING_LEVEL=info
ENV SQL_DATABASE_SERVER_HOST=farol-postgres
ENV SQL_DATABASE_SERVER_PORT=5432
ENV SQL_DATABASE_USERNAME=farol
ENV SQL_DATABASE_PASSWORD=farol
ENV ENVIRONMENT=dev
ENV REDIS_DATABASE_SERVER_HOST=farol-redis
ENV REDIS_DATABASE_INDEX=3
ENV SMTP_HOST=smtp.gmail.com
ENV SMTP_PORT=587
ENV SMTP_USERNAME=contato.vidaplano@gmail.com
ENV SMTP_PASSWORD=Farol123
ENV SMTP_USE_AUTH=true
ENV SMTP_USE_TLS=true
ENV CRON_EVERY_MIDNIGHT='0 0 0 * * ?'
ENV CRON_EVERY_MONTH='0 0 1 * * ?'
ENV CRON_DELAY_MINUTES=900000

COPY --from=build /usr/src/target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE  8080
