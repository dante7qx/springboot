FROM dante2012/java:centos-oraclejre-1.8.0_212

LABEL MAINTAINER="dante <dantefreedom@gmail.com>"

ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

ARG apUser=spirit
ARG logHome=/home/ap/logs

COPY target/springboot-log4j-0.0.1-SNAPSHOT.jar /app.jar

RUN set -eux; \
    groupadd -r ${apUser} --gid=1000; \
    useradd -r -g ${apUser} --uid=1000 ${apUser}; \
    mkdir -p ${logHome}; \
	chown -R ${apUser}:${apUser} ${logHome}

USER ${apUser}

EXPOSE 8080

VOLUME ["${logHome}"]

COPY docker-entrypoint.sh /

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
