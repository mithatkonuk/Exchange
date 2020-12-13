FROM openjdk:11-slim

MAINTAINER Mithat Konuk mithatkonuk@gmail.com

RUN mkdir -p /opt/exchange/

RUN groupadd -g 500 tomcat && \
   useradd -r -u 500 -g tomcat tomcat

RUN chown -R tomcat:tomcat /opt/exchange

ARG JAR_FILE=target/Exchange-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} /opt/exchange/Exchange-0.0.1-SNAPSHOT.jar

RUN chown -R tomcat:tomcat  /opt/exchange/Exchange-0.0.1-SNAPSHOT.jar

USER tomcat

EXPOSE 8080

ENTRYPOINT ["java","-jar","/opt/exchange/Exchange-0.0.1-SNAPSHOT.jar"]




