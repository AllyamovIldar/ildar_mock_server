FROM centos:7
RUN mkdir /data && mkdir /data/www && mkdir /data/www/static && yum -y install epel-release && yum -y install nginx && rm -v /etc/nginx/nginx.conf && yum -y install java-1.8.0-openjdk-devel
RUN mkdir /data/www/mock/
COPY ./static /data/www/static/
COPY mock_server_ildar.jar /data/www/mock/
ADD nginx.conf /etc/nginx/
RUN echo "daemon off;" >> /etc/nginx/nginx.conf
ADD entry.sh /data/www/mock/
WORKDIR /data/www/mock/
RUN chmod +x entry.sh
EXPOSE 8080 80
ENTRYPOINT ["./entry.sh"]