include .env

IMAGE_NAME=registry.heroku.com/online-banking-demo-api/web
CONTAINER_NAME=online-banking-demo-api

check-update:
	mvn versions:display-dependency-updates
	
update:
	mvn versions:use-latest-versions

test:
	mvn test
	
coverage:
	mvn test jacoco:report
	
sonar:
	mvn -Dsonar.host.url=$(SONAR_HOST_URL) -Dsonar.projectKey=online-banking-backend -Dsonar.organization=sineverba -Dsonar.login=$(SONAR_LOGIN) clean package sonar:sonar
	
build:
	docker build --tag $(IMAGE_NAME) --file ./docker/Dockerfile .
	
run:
	docker run --name $(CONTAINER_NAME) -e "PORT=9876" -p "9876:9876" $(IMAGE_NAME)
	
stop:
	docker container stop $(CONTAINER_NAME)
	docker container rm $(CONTAINER_NAME)
	
destroy:
	docker image rm $(IMAGE_NAME)
	
deploy:
	docker push $(IMAGE_NAME)
	heroku container:release web -a $(CONTAINER_NAME)
	heroku labs:enable -a $(CONTAINER_NAME) runtime-new-layer-extract
