FROM mysql/mysql-server:latest AS mysql-employees-persistence

ENV MYSQL_ROOT_PASSWORD root

ENV MYSQL_ROOT_HOST '%'

COPY docker-entrypoint-initdb.d/employees_partitioned.sql /docker-entrypoint-initdb.d/01.employees_partitioned.sql
COPY docker-entrypoint-initdb.d/load_departments.dump     /docker-entrypoint-initdb.d/load_departments.dump
COPY docker-entrypoint-initdb.d/load_employees.dump       /docker-entrypoint-initdb.d/load_employees.dump
COPY docker-entrypoint-initdb.d/load_dept_emp.dump        /docker-entrypoint-initdb.d/load_dept_emp.dump
COPY docker-entrypoint-initdb.d/load_dept_manager.dump    /docker-entrypoint-initdb.d/load_dept_manager.dump
COPY docker-entrypoint-initdb.d/load_salaries1.dump       /docker-entrypoint-initdb.d/load_salaries1.dump
COPY docker-entrypoint-initdb.d/load_salaries2.dump       /docker-entrypoint-initdb.d/load_salaries2.dump
COPY docker-entrypoint-initdb.d/load_salaries3.dump       /docker-entrypoint-initdb.d/load_salaries3.dump

EXPOSE 3306/tcp
