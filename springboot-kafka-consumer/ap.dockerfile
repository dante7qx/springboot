FROM dante2012/java:centos-oraclejre-1.8.0_212

LABEL MAINTAINER="dante <dante@dante7qx@126.com>"

ENV JAVA_OPTS="-XX:MinRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0 -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m" 

ARG apUser=spirit

RUN set -eux; \
    groupadd -r ${apUser} --gid=1000; \
    useradd -r -g ${apUser} --uid=1000 ${apUser}

COPY target/springboot-kafka-consumer-0.0.1-SNAPSHOT.jar /app.jar

USER ${apUser}

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
