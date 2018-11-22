FROM java:8
LABEL MAINTAINER="Dante <ch.sun@hnair.com>"
ENV JAVA_OPTS="-Xms1g -Xmx1g" \
	TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
## Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录
VOLUME ["/tmp"]
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
WORKDIR /app/
EXPOSE 8801
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]