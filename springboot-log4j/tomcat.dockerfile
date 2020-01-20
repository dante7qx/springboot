FROM dante2012/tomcat:8.5.46

LABEL MAINTAINER="dante <dantefreedom@gmail.com>"

ENV CATALINA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

COPY target/springboot-log4j-0.0.1-SNAPSHOT.war webapps/ROOT.war

RUN set -eux; \
    groupadd -r spirit --gid=1000; \
    useradd -r -g spirit --uid=1000 spirit; \
    mkdir -p /home/ap/logs; \
	chown -R spirit:spirit /home/ap/logs; \
    chown -R spirit:spirit .; \
    chmod 777 logs temp work
##    ; \
##	unzip -oq webapps/ROOT.war -d webapps/ROOT; \
##	rm -rf webapps/ROOT.war

USER spirit

EXPOSE 8080

VOLUME ["/home/ap/logs"]

COPY docker-entrypoint.sh /

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["catalina.sh", "run"]
