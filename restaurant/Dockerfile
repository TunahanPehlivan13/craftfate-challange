FROM adoptopenjdk/maven-openjdk11 as builder
WORKDIR /restaurant
COPY . /restaurant
RUN mvn clean install -DskipTests

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /restaurant
ARG JAR_FILE=/restaurant/target/*.jar
COPY --from=builder $JAR_FILE /restaurant/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/restaurant/app.jar"]
