# Docker image for springboot application
# VERSION 0.0.1
# Author: markrenChina

FROM adoptopenjdk:11-jre-openj9
#COPY *.jar /loginserverc.jar
MAINTAINER markrenChina <390835144@qq.com>
#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/wxpay-0.0.1-SNAPSHOT.jar wxpay-0.0.1-SNAPSHOT.jar
CMD ["--server.port=20202"]
EXPOSE 2
ENTRYPOINT ["java","-jar","/wxpay-0.0.1-SNAPSHOT.jar"]