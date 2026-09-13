FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/TaskManagment-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "TaskManagment-0.0.1-SNAPSHOT.jar"]

