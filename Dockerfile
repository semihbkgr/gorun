FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /gorun/src
COPY pom.xml /gorun
RUN mvn -f /gorun/pom.xml -q clean package -Dmaven.test.skip
FROM openjdk:17-alpine
COPY --from=build /gorun/target/gorun-server-1.0.0.jar app.jar
COPY --from=golang:1.17-alpine /usr/local/go/ /usr/local/go/
ENV PATH="/usr/local/go/bin:${PATH}"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]