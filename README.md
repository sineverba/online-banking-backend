Online Banking Demo Backend
===========================

> Demo backend project for an online banking, written with Java 17 + Spring

| Service | Github link | Demo |
| ------- | ----------- | ---- |
| Backend | [https://github.com/sineverba/online-banking-backend](https://github.com/sineverba/online-banking-backend) | N.A. |
| Frontend | [https://github.com/sineverba/online-banking-frontend](https://github.com/sineverba/online-banking-frontend) | N.A. |

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

## TDD

| Step | What | Commit # |
| ---- | ---- | -------- |
| 1 | Setup tests + Lombok | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/ca5e5863ecd7434422b41708fb55db6a5f5b77ac) |
| 2 | Add Entity | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/9ebeddc0020e7e1246ad65c3068fbe59b54688b2) |
| 3 | Add Repository | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/af84fa1dfda955b0fe779dc249cd447d573b5f6f) |
| 4 | Add Service | |
| 5 | Add Controller index + DTO + Model Mapper (into main Class) | |
