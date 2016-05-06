#!/usr/bin/env bash

for size in {500..5000..500}
do
    docker-compose -f dockers/docker-compose-stresstest.yml up server
    for i in {1..4}
    do
        mvn -pl gatling-test gatling:execute -Dgatling.simulationClass=com.excilys.computerdatabase.gatling.simulation.ComputerDatabaseSimulation -Dstresstest.maxUsers=size
    done
    docker-compose -f dockers/docker-compose-stresstest.yml down --rmi=local
done