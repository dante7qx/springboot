FROM dante2012/openjdk:1.8.0_222

LABEL MAINTAINER="sunchao.zh <sunchao.bj@ccbft.com>"

ENV JAVA_OPTS="-Xms1024m -Xmx1024m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"
ENV WORKSPACE="/app"

WORKDIR $WORKSPACE

ADD target/springboot-log4j-0.0.1-SNAPSHOT.jar app.jar

RUN groupadd -r spirit && useradd -r -g spirit spirit && \
    mkdir -p $WORKSPACE/logs && \
    chown -R spirit:spirit $WORKSPACE 

USER spirit

EXPOSE 8080

VOLUME ["$WORKSPACE/logs"]

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar $WORKSPACE/app.jar" ]