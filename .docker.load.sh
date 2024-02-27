#!/bin/sh
source $(dirname $0)/.docker..common.sh
docker exec "$name" /bin/sh -c mysql -u"$user" -p"$pass" < docker-entrypoint-initdb.d/employees_partitioned.sql
