FROM openjdk:11.0-jre-slim

ADD ./target/mchat-1.0-SNAPSHOT.jar /app/mchat.jar
ADD ./mchat-ui/dist /app/ui
WORKDIR /app
CMD java -jar mchat.jar