FROM dante2012/tomcat:8.5.51-centos-oraclejdk 

LABEL MAINTAINER="dante <dantefreedom@gmail.com>"

ENV CATALINA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

ARG apUser=spirit
ARG logHome=/home/ap/logs

COPY target/springboot-log4j-0.0.1-SNAPSHOT.war webapps/ROOT.war

RUN set -eux; \
    groupadd -r ${apUser} --gid=1000; \
    useradd -r -g ${apUser} --uid=1000 ${apUser}; \
    mkdir -p ${logHome}; \
	chown -R ${apUser}:${apUser} ${logHome}; \
    chown -R ${apUser}:${apUser} .; \
    chmod 777 logs temp work ; \    
	unzip -oq webapps/ROOT.war -d webapps/ROOT; \
	rm -rf webapps/ROOT.war

USER ${apUser}

EXPOSE 8080

VOLUME ["${logHome}"]

COPY docker-entrypoint.sh /

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["catalina.sh", "run"]
