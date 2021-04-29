FROM openjdk:11.0.10-jdk
COPY build/libs/proj-websocket-*-all.jar proj-websocket.jar
CMD java -jar proj-websocket.jar