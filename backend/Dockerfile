# Multi-stage build
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -B dependency:go-offline
COPY ./src ./src
RUN ./mvnw -B package

FROM eclipse-temurin:17-jre-jammy
LABEL org.opencontainers.image.authors="Philippe Collet philippe.collet@univ-cotedazur.fr"
ARG JAR_FILE
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
WORKDIR /opt/app
COPY --from=builder /opt/app/target/${JAR_FILE} ./app.jar
RUN chown -R dockeruser:dockergroup /opt/app
EXPOSE 8080
# Starting the service (shell form of ENTRYPOINT used for substitution)
USER dockeruser
ENTRYPOINT java -jar /opt/app/app.jar --bank.host.baseurl=$BANK_WITH_PORT --scheduler.host.baseurl=$SCHEDULER_WITH_PORT
