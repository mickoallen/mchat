FROM openjdk:11.0-jre-slim

ADD ./target/mchat-1.0-SNAPSHOT.jar /app/mchat.jar
ADD ./ui/dist /app/ui
WORKDIR /app

EXPOSE 8080

CMD java -jar mchat.jar