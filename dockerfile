FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/*.jar /*.jar
ENTRYPOINT ["java","-jar","/*.jar"]
