Online Banking Demo Backend
===========================

> Demo backend project for an online banking, written with Java 17 + Spring

| Service | Github link | Demo |
| ------- | ----------- | ---- |
| Backend | [https://github.com/sineverba/online-banking-backend](https://github.com/sineverba/online-banking-backend) | [Swagger](https://bitbankapi.k2p.it/swagger-ui/index.html) |
| Frontend | [https://github.com/sineverba/online-banking-frontend](https://github.com/sineverba/online-banking-frontend) | [Netlify](https://bit-bank.netlify.app) - [Vercel](https://online-banking-frontend.vercel.app) | [Custom](https://bitbank.k2p.it/) |

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

## Development with VSCode

Extensions required (open `CTRL + P` and type the extension as following)

| Extension | Developer | Link |
| --------- | --------- | ---- |
| Spring Boot Extension Pack | VMware | ext install vmware.vscode-boot-dev-pack |
| Extension Pack for Java | Microsoft | ext install vscjava.vscode-java-pack |

## Local development with Docker

There is a dummy database, pre-populated. At first spin, MySQL will re-populate the database with some data.

## How generate a random string to use for Jwt secret

`$ openssl rand -base64 90`

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
