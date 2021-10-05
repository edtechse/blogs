#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
RUN mvn -f /home/app/pom.xml verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=blogs-api -Dsonar.login=0a7bb074e5008b1460c3cfcc6b5b2a4883e4943e

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/blogs-0.0.1-SNAPSHOT.jar /usr/local/lib/blogs.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/usr/local/lib/blogs.jar"]
