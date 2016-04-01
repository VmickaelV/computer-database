#!/bin/bash

docker build -t bdd-mysql -f dockers/db/Dockerfile_mysql .
docker run --name bdd -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d --net=private bdd-mysql

if [[ -n  $(docker ps -q -f status=running -f name=bdd) ]]; then
	docker stop bdd
fi

if [[ -n  $(docker ps -q -f name=bdd) ]]; then
	docker rm bdd
fi

docker network create private

docker run --privileged --name docker -d --net=private -v /var/lib/docker docker:dind

docker build -t mviegas/jenkins -f dockers/Dockerfile_jenkins .

docker run -p 8080:8080 -p 50000:50000 --net=private --rm --name jenkins mviegas/jenkins

