FROM dante2012/java:jre8

LABEL MAINTAINER="dante <sunchao@ccbft.com>"

ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m" 

WORKDIR /AppServer

ADD target/springboot-data-redis-0.0.1-SNAPSHOT.jar app.jar

RUN sh -c 'touch /AppServer/app.jar'

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /AppServer/app.jar" ]