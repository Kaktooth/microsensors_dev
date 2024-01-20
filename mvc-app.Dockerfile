FROM eclipse-temurin:17-jdk-alpine
VOLUME tmp/
ADD ./open-sensor-data.systems.p12 .
ADD ./open-sensor-data.systems.csr .
ADD ./open-sensor-data.systems.crt .
ADD ./open-sensor-data.systems.key .
COPY build/libs/microsensors-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 443
ENTRYPOINT ["java","-jar","/app.jar"]