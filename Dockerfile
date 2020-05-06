FROM openjdk:8-jdk

MAINTAINER atmaram

RUN rm -rf /usr/local/tomcat/webapps/*

COPY /target/sparkjava-hello-world-1.0.war /usr/local/tomcat/webapps/sparkjava-hello-world-1.0.war

CMD ["catalina.sh","run"]
