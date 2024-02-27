#!/bin/sh
### docker exec -it db1 mysql -uuser1 -ppass1 -Ddb1
source $(dirname $0)/.docker..common.sh
docker exec -it $name "$name" -u"$user" -p"$pass" -D"$db"
