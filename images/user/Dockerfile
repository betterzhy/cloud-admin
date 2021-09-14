FROM openjdk:8-jre
ENV APP_PATH=/apps
WORKDIR $APP_PATH
COPY cloud-user-1.0-SNAPSHOT.jar $APP_PATH/app.jar
EXPOSE 20082
ENTRYPOINT ["java","-jar"]
CMD ["app.jar"]