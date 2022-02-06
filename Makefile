include .env

test:
	mvn test
	
coverage:
	mvn test jacoco:report
	
sonar:
	mvn -Dsonar.host.url=http://localhost:9000 -Dsonar.projectKey=online-banking-backend -Dsonar.organization=sineverba -Dsonar.login=$(SONAR_LOGIN) clean package sonar:sonar
