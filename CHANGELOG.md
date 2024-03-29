# 1.3.0
+ Update dependencies
+ Update dependencies
+ Fix Sonarqube
+ Remove all MFA

## 1.2.2
+ Fix missing Maven repository

## 1.2.1
+ Remove all MFA dependencies

## 1.2.0
+ Change OTP library
+ Add EntityNotFoundException
+ Add method to store a temp secret
+ Generate a secret store
+ Login via temp secret

## 1.1.0
+ Upgrade dependencies
+ Fix deprecated CORS
+ Add column `secret_mfa` to database
+ Add utility to generate a random string
+ Store secret when save a new user
+ Add V2 controller for login
+ Add `findById` method for user
+ Refactor `findById` method for user
+ Add route for verify MFA
+ Add MFAService
+ Complete MFA

## 1.0.0
+ Refactor Semaphore
+ Upgrade dependencies

## 0.20.1
+ Fix multiplatform

## 0.20.0
+ Upgrade dependencies, reorganize Docker and refactor Semaphore

## 0.19.0
+ Fix some warning during boot
+ Add get single transaction

## 0.18.0
+ Upgraded dependencies
+ Add roles list to me route
+ Create multiarch image
+ Upgrade dependencies
+ Upgrade Spring Starter Parent + Fix H2 + Fix some warning on STS
+ Fix deprecated SecurityConfiguration
+ Upgrade docker image

## 0.16.0
+ Fix some Spring warnings
+ Refactor balance not enough exception
+ Move to Heroku
+ Rename registration in subscription
+ Refactor `me` controller
+ Change type of return of Response
+ Move ERole enums to `constants` package
+ Fix link in README and MySql dump

## 0.15.0
+ Add `Roles` Repository
+ Add roles to UsersEntity
+ Add authorities to UserDetails
+ Implement `findByName` in Roles Repository
+ Add set authorities in SecurityContext
+ Add get roles list
+ Return roles on login
+ Add check on routes for roles
+ Handle AccessDenied Exception
+ Add Roles Service
+ Add Role not found Exception
+ Refactor show method of RolesService
+ Add Customer Role when user subscribe

## 0.14.0
+ Upgrade dependencies
+ Refactor BankAccountTransactionsDTO for API post
+ Refactor repository package test in repositories
+ Add `me` route

## 0.13.0
+ Add `BalanceNotEnoughException`
+ Refactor `BalanceNotEnoughException`
+ Refactor CORS
+ Validate registration
+ Move CORS to application properties
+ Add logger configuration

## 0.12.0
+ Generate token from token
+ Add Refreh Token route
+ Get expiry date from token
+ Add Expiry at on jwt response on login
+ Add Expiry at on jwt response on refresh


## 0.11.0
+ Remove unused imports
+ Fix "Type safety: The expression of type Page needs unchecked conversion to conform to Page<BankAccountTransactionsEntity>"
+ Return JSON if not authorized
+ Add Bank Account validation
+ Add Login validation

## 0.10.0
+ Change swagger server
+ Merge commons application properties

## 0.9.0
+ Add swagger

## 0.8.0
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