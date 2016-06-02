Computer-database project
===========================  

## Content
This project was made in order to training with plenty of concepts of computings, and some tools.

This project used :
* Tomcat
* Spring
    * MVC
    * Transaction
    * Security (with login)
    * WebServices with REST
    * Localization i18n
* Hibernate Validation
* JPA and Hibernate (with Criteria)
* Docker & docker Compose
    * Dockerfile, dind, ...
* Maven & organization with modules
    * Maven configuration with rapports
    * CheckStyle
* Concepts of DAO, DTO, Services, Mappers, Paginator
* Logs (LogBack)
* JSPs, JSTL, and Tags
* jQuery lib (jquery, jQuery UI, jQuery Validation)
* Units Testing & Integration testing with Selenium
* Continuous delivery (Jenkins)

* Old concepts (in previous commits)
    * Servlets
    * JDBC
    * ThreadLocal
    * Singleton enum Pattern
    * Connection Pool (today managed by Spring Datasource)
    * Spring Security (authentication with Digest)

## Utilisation

First at all, be into the root folder of the project

To launch the test server :

```bash
docker-compose -f dockers/docker-compose-envtest.yml up test_server
```

To launch Unit tests :
```bash
docker-compose -f dockers/docker-compose-envtest.yml up unit_test
```

To launch Integration testing :
```bash
docker-compose -f dockers/docker-compose-envtest.yml up selenium_test
```

To launch Production Server :
(if the docker is not build, you need to generate the war of the front-webapp project)
```bash
docker-compose -f dockers/docker-compose-envtest.yml up tomcat_webapp
```

To launch stress tests :
TODO

## Points to view
#### 4.4.3 Glazer Container Agent
The Glazer Container Agent is a simple webapp that allow us to manage Docker containers.

Create a Docker container with a Glazer Container Agent. This container will be your staging server. Like the Jenkins container, this container must be able to start containers (Docker in Docker).

Create two Docker images: one for the computer database webapp and one for the mysql. Once again use docker-compose to describe your services. Push them to DockerHub.

Add another job in your Jenkins that updates the computer-database-webapp image with the latest successful war and pushes it to DockerHub. Then ask your Glazer Container Agent container to create a new container from the latest image. This job must be triggered only if the UT tests pass.

#### 4.5.2. Point overview: Continuous Integration (t0 + 18 days)
Jenkins + DinD: Which service actually starts the containers ? How to share directories between containers ?  
Glazer Container Agent + DinD: How to handle container port mapping ? (2 solutions)  
DockerHub: Automated builds limitations ?
