Online Banking Demo Backend
===========================

> Demo backend project for an online banking, written with Java 17 + Spring

| Service | Github link | Demo |
| ------- | ----------- | ---- |
| Backend | [https://github.com/sineverba/online-banking-backend](https://github.com/sineverba/online-banking-backend) | [Swagger](https://https://online-banking-backend-api.herokuapp.com) |
| Frontend | [https://github.com/sineverba/online-banking-frontend](https://github.com/sineverba/online-banking-frontend) | [Demo](https://bitbank.k2p.it) |

__This project uses:__

+ TDD
+ 100% test coverage
+ Dockerization
+ CI/CD

| CI/CD | Link |
| ----- | ---- |
| Circle CI | [![CircleCI](https://circleci.com/gh/sineverba/online-banking-backend.svg?style=svg)](https://circleci.com/gh/sineverba/online-banking-backend) |
| Semaphore CI | [![Build Status](https://sineverba.semaphoreci.com/badges/online-banking-backend.svg)](https://sineverba.semaphoreci.com/projects/online-banking-backend) |
| Sonarqube | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sineverba_online-banking-backend&metric=alert_status)](https://sonarcloud.io/dashboard?id=sineverba_online-banking-backend) |
| Coveralls | [![Coverage Status](https://coveralls.io/repos/github/sineverba/online-banking-backend/badge.svg?branch=master)](https://coveralls.io/github/sineverba/online-banking-backend?branch=master) |

## Local development with Docker

There is a dummy database, pre-populated. At first spin, MySQL will re-populate the database with some data.

With a custom Sonar setup, launch them with `$ docker-compose profile --dev up` and you will not get Sonar images.

## How populate database

If you don't use current database dump, you need to populate `roles` table with:


```sql
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_CUSTOMER');
```


## Test
`$ make test`

## Coverage
`$ make coverage`

> Coverage will be set into `target/site/jacoco`

## Local SonarQube

+ Copy .env.bak into .env
+ Start containers (`docker-compose up -d`)
+ Connect to SonarQube (at `http://localhost:9000")
+ First credentials are `admin // admin`
+ To associate a new project
  + Manually
  + Project Display Name: Online Banking Backend
  + Project Key: online-banking-backend
  + Setup
  + Locally
  + Generate a token called `online-banking-backend`
  + In `.env`, place as `SONAR_LOGIN` the token value
  + Continue > Run analyses ... > Other > Linux
  + Launch scanner with `make sonar`

### Next Sonar scan

+ `make sonar`
