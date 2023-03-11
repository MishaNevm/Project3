FROM openjdk:17-alpine
ADD /target/Project3-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]