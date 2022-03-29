FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app
COPY ../mvnw .
COPY ../.mvn .mvn
COPY ../pom.xml .
COPY ../src src
RUN dos2unix mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","api_gateway.ApiGatewayApplication"]

#FROM adoptopenjdk/openjdk11:latest
#
#ARG JAR_FILE=target/*.jar
#
#RUN mkdir /opt/apigateway-service
#
#COPY ${JAR_FILE} /opt/apigateway-service/apigateway-service.jar
#
#ENTRYPOINT ["java", "-jar", "/opt/apigateway-service/apigateway-service.jar"]