FROM tomcat:9.0.30-jdk8-adoptopenjdk-openj9

ADD latest-grafana-kafka-rest*.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]