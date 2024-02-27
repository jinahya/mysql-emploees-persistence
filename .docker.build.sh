#!/bin/sh
source $(dirname $0)/.docker..common.sh
docker image rm "$image"
docker build -t "$image" .
