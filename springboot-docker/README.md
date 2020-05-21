## Docker 命令

- 1. 构建  
	`docker build -t="镜像名:版本号" .`

- 2. 运行  

 	`docker run -e "SPRING_PROFILES_ACTIVE=dev" -e "JAVA_OPTS=-Xmx1g -Xms512m" -d -p 8801:8801 --name <容器名> <镜像名>`
 	
 	`docker run -e "SPRING_PROFILES_ACTIVE=dev" -e "JAVA_OPTS=-Xmx1g -Xms512m"  -d -p 8801:8801 --name dante-sd dante2012/springboot-docker 
 	`
- 3. 进入容器
	`docker exec -it dante-sd /bin/bash`

## 使用maven构建Docker Image

- 1. 添加插件

```    xml
<properties>
   <docker.image.prefix>dante2012</docker.image.prefix>
</properties>
<build>
    <plugins>
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>docker-maven-plugin</artifactId>
            <version>0.4.14</version>
            <configuration>
            	<!-- 如果要将docker镜像push到DockerHub上去的话，这边的路径要和repo路径一致 -->
                <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
                <dockerDirectory>src/main/docker</dockerDirectory>
                <resources>
                    <resource>
                        <targetPath>/</targetPath>
                        <directory>${project.build.directory}</directory>
                        <include>${project.build.finalName}.jar</include>
                    </resource>
                </resources>
                <!-- 以下两行是为了docker push到DockerHub使用的。 -->
                <serverId>docker-hub</serverId>
                <registryUrl>https://index.docker.io/v1/</registryUrl>
            </configuration>
        </plugin>
    </plugins>
</build>
```

- 2.在 src/main/docker下编写Dockerfile

- 3. 执行命令

```bash
mvn package -Dmaven.test.skip=true docker:build 
docker push dante2012/springboot-docker

或

mvn package -Dmaven.test.skip=true docker:build -DpushImage
```