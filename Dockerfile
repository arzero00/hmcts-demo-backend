FROM eclipse-temurin:21-jre-jammy
EXPOSE 4000
RUN mkdir /app

COPY ./build/libs/hmcts-demo-backend.jar /app/hmcts-demo-backend.jar

ENTRYPOINT ["java", "-jar", "/app/hmcts-demo-backend.jar"]
