#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app
COPY pom.xml /home/app
# Download the package and make it cached in docker image
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

# Copy the actual code
COPY src /home/app/src
# Then build the code
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/blogs-0.0.1-SNAPSHOT.jar /usr/local/lib/blogs.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/usr/local/lib/blogs.jar"]