# 0.8.0
+ Rename repositories in repository
+ Remove V1 from packages
+ Remove test from packages tests
+ Add pagination

## 0.7.0
+ Add balance to BankAccountTransactionsRepository
+ Add balance to BankAccountTransactionsService
+ Add Balance controller

## 0.6.0
+ Upgrade dependencies
+ Refactor Semaphore
+ Add production configuration
+ Refactor Response Entity for JWT
+ Refactor docker-compose for production
+ Refactor Response Entity for PingController
+ Migrate to custom Sonarqube. Fix double CSRF disable
+ Refactor Message Entity for Registration Controller

## 0.5.0
+ Enable registration URL only when APP properties is true

## 0.4.0
+ Remove JWT configuration part 1
+ Add JWT - Part 1 - Application Property + Entity
+ Add JWT - Part 2 - Add UserRepository
+ Add JWT - Part 3 - Add UserDetailsImpl
+ Add JWT - Part 4 - Add UserDetailsServiceImpl
+ Add JWT - Part 5 - Add AuthEntryPointJwt + Rename UserDetailsServiceImpl
+ Add JWT - Part 6 - Add JwtUtils
+ Add JWT - Part 7 - Add AuthTokenFilter
+ Add JWT - Part 8 - Configure Spring Security
+ Add JWT - Part 9 - Add UsersService POST (create)
+ Add JWT - Part 10 - Add AuthController Register
+ Add JWT - Part 11 - Add AuthController Login
+ Add JWT - Part 12 - Set route under authenticated token


## 0.3.0
+ Upgrade dependencies
+ Add JWT - Part 1
+ Fix CORS issue

## 0.2.0
+ Setup tests
+ Add CI/CD
+ Add Bank Account Transactions entity
+ Add Bank Account Transactions repository
+ Add Bank Account Transactions service
+ Add Bank Account Transactions controller + DTO
+ Add Docker for deploy
+ Add Bank Account Transactions service POST
+ Add Bank Account Transactions controller POST
+ Add Ping service GET
+ Add Ping controller GET
+ Add exclusion in pom.xml for 100% coverage

## 0.1.0
+ First commit