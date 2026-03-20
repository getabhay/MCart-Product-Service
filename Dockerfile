FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*-exec.jar /app/mcart-product-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/mcart-product-service.jar"]