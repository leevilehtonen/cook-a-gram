# [cook-a-gram](https://cook-a-gram.herokuapp.com/)
[![Build Status](https://travis-ci.org/leevilehtonen/cook-a-gram.svg?branch=master)](https://travis-ci.org/leevilehtonen/cook-a-gram)
[![codebeat badge](https://codebeat.co/badges/34c2a02e-2487-487e-b121-50fa042b2908)](https://codebeat.co/projects/github-com-leevilehtonen-cook-a-gram-master)
[![codecov](https://codecov.io/gh/leevilehtonen/cook-a-gram/branch/master/graph/badge.svg)](https://codecov.io/gh/leevilehtonen/cook-a-gram)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

Social picture sharing app

- [Live demo](https://cook-a-gram.herokuapp.com/) - Live demo username: `tester`, password: `123456`
- [Documentation](documentation/index.md) - Documentation index

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Built with

What tech have been used in this project serverside

- [Java JDK](http://www.oracle.com/technetwork/java/javase/overview/index.html) - Java Platform lets you develop and deploy Java applications on desktops and servers.
- [Spring Boot](http://projects.spring.io/spring-boot/) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.
- [PostgreSQL](https://www.postgresql.org/about/) - PostgreSQL is a powerful, open source object-relational database system.
- [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool. 
- [Thymeleaf](http://www.thymeleaf.org/) - Thymeleaf is a modern server-side Java template engine for both web and standalone environments.

### Prerequisites

See Built with section

### Installing

A step by step series of examples that tell you have to get a development env running

```sh
git clone https://github.com/leevilehtonen/cook-a-gram.git //or with ssh
cd cook-a-gram
mvn spring-boot:run
```
More in about how you can run spring-boot application, see: [Spring Docs: Running your application](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)


## Running the tests

```sh
mvn clean test
```

## Deployment

For running production java:
```sh
java $JAVA_OPTS -Dspring.profiles.active=production -Dserver.port=$PORT -jar target/*.jar
```

For running with Maven:
```sh
mvn spring-boot:run
```

If you want to deliver your own copy of cook-a-gram, it's very easy to serve at Heroku as procfile is attached in repo


## Contributing

Make merge ready PR or contact me with feature suggestions.

## Authors

* **Leevi Lehtonen** - *Initial work* - [LeeviLehtonen](https://github.com/leevilehtonen)

See also the list of [contributors](https://github.com/leevilehtonen/cook-a-gram/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments



