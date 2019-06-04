FROM openjdk:8-jre
MAINTAINER Joher <JoherYu@163.com>
RUN mkdir /app
COPY share-photo-eureka-1.0.0-SNAPSHOT.jar /app/
CMD java -jar /app/share-photo-eureka-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
EXPOSE 7000
