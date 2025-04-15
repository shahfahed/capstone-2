FROM ubuntu:latest

RUN apt update && \
    apt install -y nginx && \
    apt clean

COPY images /var/www/html
COPY index.html /var/www/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
