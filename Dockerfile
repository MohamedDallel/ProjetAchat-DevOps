FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD http://192.168.2.53:8081/repository/maven-repository/tn/esprit/rh/achat/1.0/achat-1.0.jar achat-1.0.jar
ENTRYPOINT ["java","-jar","/achat-1.0.jar"]