#!/usr/bin/env bash

docker-compose -f dockers/docker-compose-envtest.yml up --no-color selenium_test

docker-compose -f dockers/docker-compose-envtest.yml down --rmi=local