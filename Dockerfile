FROM openjdk:11
RUN groupadd -S spring && useradd -S spring -G spring
USER spring:spring
ENTRYPOINT ["java","-cp","app:app/lib/*","blogs.BlogsApplication"]
