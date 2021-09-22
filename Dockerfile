FROM openjdk:11
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ENTRYPOINT ["java","-cp","app:app/lib/*","blogs.BlogsApplication"]