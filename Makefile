include .env

IMAGE_NAME=registry.gitlab.com/cicdprojects/online-banking-backend
CONTAINER_NAME=online-banking-backend
VERSION=0.20.1-dev
BUILDX_VERSION=0.10.0
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
		-Dsonar.host.url=$(SONAR_HOST_URL) \
		-Dsonar.projectKey=online-banking-backend \
		-Dsonar.organization=sineverba \
		-Dsonar.login=$(SONAR_LOGIN) \
		clean package sonar:sonar
	
build:
	docker build \
		--tag $(IMAGE_NAME):$(VERSION) \
		--tag $(IMAGE_NAME):latest \
		--file ./dockerfiles/production/build/docker/Dockerfile "."
	
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
		--tag $(IMAGE_NAME):latest \
		--push \
		--file ./dockerfiles/production/build/docker/Dockerfile "."
	
spin:
	docker run --name $(CONTAINER_NAME) -e "PORT=9876" -p "9876:9876" $(IMAGE_NAME):$(VERSION)
	
stop:
	docker container stop $(CONTAINER_NAME)
	docker container rm $(CONTAINER_NAME)
	
destroy:
	docker image rm $(IMAGE_NAME):$(VERSION) $(IMAGE_NAME):latest
	
push:
	docker push $(IMAGE_NAME):$(VERSION) $(IMAGE_NAME):latest
