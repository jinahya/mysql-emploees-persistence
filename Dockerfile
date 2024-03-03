FROM mysql/mysql-server:latest AS mysql-employees-persistence

ENV MYSQL_ROOT_PASSWORD root

ENV MYSQL_ROOT_HOST '%'

COPY .docker-entrypoint-initdb.d/employees.sql    /docker-entrypoint-initdb.d/employees.sql
COPY .docker-entrypoint-initdb.d/*.dump           /docker-entrypoint-initdb.d/
COPY .docker-entrypoint-initdb.d/show_elapsed.sql /docker-entrypoint-initdb.d/show_elapsed.sql

EXPOSE 3306/tcp
