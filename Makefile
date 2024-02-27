#include .env

IMAGE_NAME=registry.gitlab.com/cicdprojects/online-banking-backend
CONTAINER_NAME=online-banking-backend
VERSION=1.2.2-dev
BUILDX_VERSION=0.12.1
BINFMT_VERSION=qemu-v7.0.0-28

check-update:
	mvn versions:display-dependency-updates
	
update:
	mvn versions:use-latest-versions

test:
	mvn test
	
coverage:
	mvn test jacoco:report
	
sonar:
	mvn \
		-Dsonar.host.url=${SONAR_HOST_URL} \
		-Dsonar.projectKey=online-banking-backend \
		-Dsonar.organization=sineverba \
		clean package sonar:sonar

cicd:
	docker build \
		--tag sineverba/$(CONTAINER_NAME)-cicd:$(VERSION) \
		--file ./dockerfiles/cicd/Dockerfile \
		"."
	docker image push sineverba/$(CONTAINER_NAME)-cicd:$(VERSION)
	
build:
	docker build \
		--tag $(IMAGE_NAME):$(VERSION) \
		--file ./dockerfiles/production/build/docker/Dockerfile \
		"."
	
preparemulti:
	mkdir -vp ~/.docker/cli-plugins
	curl -L "https://github.com/docker/buildx/releases/download/v$(BUILDX_VERSION)/buildx-v$(BUILDX_VERSION).linux-amd64" > ~/.docker/cli-plugins/docker-buildx
	chmod a+x ~/.docker/cli-plugins/docker-buildx
	docker buildx version
	docker run --rm --privileged tonistiigi/binfmt:$(BINFMT_VERSION) --install all
	docker buildx ls
	docker buildx rm multiarch
	docker buildx create --name multiarch --driver docker-container --use
	docker buildx inspect --bootstrap --builder multiarch
	
multi:
	docker buildx build \
		--platform linux/amd64,linux/arm64/v8 \
		--tag $(IMAGE_NAME):$(VERSION) \
		--file ./dockerfiles/production/build/docker/Dockerfile "."
	
spin:
	docker run --rm --name $(CONTAINER_NAME) \
		--env-file ./dockerfiles/production/deploy/.env \
		-e "PORT=8080" \
		-e "JAWSDB_MARIA_URL=mysql://user:password@localhost:3306/bitbank?autoReconnect=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true" \
		-e "JWT_SECRET=8OlQiX4kLEjCY9GTXNXy5kmEu5eoPURthJzZa6g1cS4eqd5kl0Ma0Z9zUqKG3Lft3AFQN6NgZ9E3V7dE+6lTxW1Gs2eQvwkATQzKe99vkbhQ34ENtTYeVHA" \
		--network host \
		$(IMAGE_NAME):$(VERSION)
	
stop:
	docker container stop $(CONTAINER_NAME)
	docker container rm $(CONTAINER_NAME)
	
destroy:
	docker image rm $(IMAGE_NAME):$(VERSION)
	docker image rm eclipse-temurin:17.0.7_7-jdk-jammy
	docker image rm eclipse-temurin:17.0.7_7-jre-jammy
