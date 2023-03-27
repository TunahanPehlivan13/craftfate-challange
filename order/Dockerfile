FROM adoptopenjdk/maven-openjdk11 as builder
WORKDIR /order
COPY . /order
RUN mvn clean install -DskipTests

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /order
ARG JAR_FILE=/order/target/*.jar
COPY --from=builder $JAR_FILE /order/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/order/app.jar"]
