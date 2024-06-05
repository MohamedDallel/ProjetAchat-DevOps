FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/stock-1.0.0.jar /stock-1.0.0.jar
ENTRYPOINT ["java","-jar","/stock-1.0.0.jar"