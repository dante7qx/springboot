FROM dante2012/tomcat:8.5.51-centos-oraclejdk 

LABEL MAINTAINER="dante <dantefreedom@gmail.com>"

ENV CATALINA_OPTS="-XX:MinRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0 -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

ARG apUser=spirit
ARG logHome=/home/ap/logs

COPY target/springboot-log4j-0.0.1-SNAPSHOT.war webapps/ROOT.war
COPY docker-entrypoint.sh /

RUN set -eux; \
    groupadd -r ${apUser} --gid=1000; \
    useradd -r -g ${apUser} --uid=1000 ${apUser}; \
    mkdir -p ${logHome}; \
	chown -R ${apUser}:${apUser} ${logHome}; \
    chown -R ${apUser}:${apUser} .; \
    chmod 777 logs temp work; \  
    chmod 755 /docker-entrypoint.sh; \  
	unzip -oq webapps/ROOT.war -d webapps/ROOT; \
	rm -rf webapps/ROOT.war

USER ${apUser}

EXPOSE 8080

VOLUME ["${logHome}"]

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["catalina.sh", "run"]
