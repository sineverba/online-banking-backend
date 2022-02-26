Online Banking Demo Backend
===========================

> Demo backend project for an online banking, written with Java 17 + Spring

| Service | Github link | Demo |
| ------- | ----------- | ---- |
| Backend | [https://github.com/sineverba/online-banking-backend](https://github.com/sineverba/online-banking-backend) | [API](TODO) |
| Frontend | [https://github.com/sineverba/online-banking-frontend](https://github.com/sineverba/online-banking-frontend) | [Netlify](TODO) - [Vercel](TODO) |

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

| Step | What | HTTP verb | Commit # |
| ---- | ---- | --------- | -------- |
| 1 | Setup tests + Lombok | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/ca5e5863ecd7434422b41708fb55db6a5f5b77ac) |
| 2 | Add Entity | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/9ebeddc0020e7e1246ad65c3068fbe59b54688b2) |
| 3 | Add Repository | GET / findAll() | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/af84fa1dfda955b0fe779dc249cd447d573b5f6f) |
| 4 | Add Service | GET / findAll() | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/0fba806b852d2ca84b1d67a4f4f859261fba9907) |
| 5 | Add Controller index + DTO + Model Mapper (into main Class) | GET / findAll | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/4aabe04ad4f21f3e342d33edff7832ab92e1e551) |
| 6 | Add Service | POST | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/3915553d740e95f711245cb38b621b6ea981d641) |
| 7 | Add Controller | POST | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/0f9eebb849d3785065d7f37ac252c34ff30a0a56) |
| 8 | Add Ping Service | GET | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/82745431e03beb29638afbdeff1619da031eff7b) |
| 9 | Add Ping Controller | GET | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/6281f4e0827475dd22763fce23f798f37218f919) |
| 10 | Add JWT - Part 1 - Add JWT - Part 1 - Application Property + Entity | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/ca9306602b5581414262e0024cfae3c720eee506) |
| 11 | Add JWT - Part 2 - Add UserRepository | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/2de66cc0541b4dc6a5ef7e1e7eac36320c85de3e) |
| 12 | Add JWT - Part 3 - Add UserDetailsImpl | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/1ab5673f358bcf5607aa417009c3b5039efa8e45) |
| 13 | Add JWT - Part 4 - Add UserDetailsServiceImpl | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/14377c80d0f8dd1cd182f3cb26e3ca481a22efe6) |
| 14 | Add JWT - Part 5 - Add AuthEntryPointJwt | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/b803015087d0817de3549ae558e59a9bd39c0380) |
| 15 | Add JWT - Part 6 - Add JwtUtils | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/95f33c994582566c09776fb451bf59238e07dc75) |
| 16 | Add JWT - Part 7 - Add AuthTokenFilter | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/b9bd76f532a1f4ac75b209320115febf5288d5c4) |
| 17 | Add JWT - Part 8 - Configure Spring Security | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/5c5a2231f1dc127c2030c91bcbf230c006744308) |
| 18 | Add JWT - Part 9 - Add UsersService POST (create) | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/59e66d093863d88f771b87350bb076d86759dcba) |
| 19 | Add JWT - Part 10 - Add AuthController Register | POST | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/4b424a76e3c4b2f90768f07843bfda84585db260) |
| 20 | Add JWT - Part 11 - Add AuthController Login | POST | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/c16a2e40a1b76959771b269c9fc94250ec42acc5) |
| 21 | Add JWT - Part 12 - Set route under authenticated token | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/73037762c84f04f419d405b8fee7f2c229625f35) |
| 22 | Add Pagination | N.A. | [Link to commit](https://github.com/sineverba/online-banking-backend/commit/8e45e02c2a2761d67f889173e51ae82e6dd95fdc)  |
| 23 | Add Swagger | N.A. | [Link to commit]() |