FROM maven:3.8.6-openjdk-8
COPY target/swplanets-0.0.1-SNAPSHOT.jar swplanets-0.0.1.jar
ENTRYPOINT ["java","-jar","/swplanets-0.0.1.jar"]

