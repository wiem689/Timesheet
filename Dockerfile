FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/Timesheet-0.0.1-SNAPSHOT.jar Timesheet-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Timesheet-0.0.1-SNAPSHOT.jar"]
