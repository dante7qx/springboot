# Dockerfile to create Spring boot docker image
# Base image
FROM java:8

LABEL MAINTAINER="Dante <ch.sun@hnair.com>"

ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m" \
	TZ=Asia/Shanghai
# 只需要设置 ENV TZ=Asia/Shanghai 就可以修改时区
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME ["/logs"]

COPY springboot-docker-0.0.1-SNAPSHOT.jar app.jar

# 使 app.jar 具有文件修改时间（默认情况下，Docker创建所有容器文件处于“未修改”状态）
RUN sh -c 'touch /app.jar'

# -Djava.security.egd=file:/dev/./urandom -> 加快 Tomcat 启动速度
# 默认是 file:/dev/random，使用 /dev/random 产生随机数的方式必须要保证熵足够大，才能够产生足够的随机数支持连接，否则系统就会产生等待，直到有足够的随机数再进行连接，这样就有了延时。

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
# ENTRYPOINT [ "java", "-jar", "app.jar", "-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k", "-Djava.security.egd=file:/dev/./urandom" ]