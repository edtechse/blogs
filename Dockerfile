FROM openjdk:11
RUN groupadd -r spring && useradd -r -gspring spring
USER spring:spring
ENTRYPOINT ["java","-cp","app:app/lib/*","blogs.BlogsApplication"]
