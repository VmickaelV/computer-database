#!/bin/bash

docker network create private

docker run --privileged --name docker -d --net=private -v /var/lib/docker docker:dind

docker build -t mviegas/jenkins -f dockers/Dockerfile_jenkins .

docker run -p 8080:8080 -p 50000:50000 --net=private --rm --name jenkins mviegas/jenkins

