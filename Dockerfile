FROM openjdk:8-jre-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/*-thorntail.jar ./app.jar
RUN chmod 775 /work
EXPOSE 8080
CMD ["java","-jar","app.jar"]
