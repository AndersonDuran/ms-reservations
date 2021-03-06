default: start logs

service:=ms-reservations
project:=microservices

.PHONY: start
start:
	docker-compose -p ${project} up -d

.PHONY: stop
stop:
	docker-compose -p ${project} down

.PHONY: restart
restart: stop start

.PHONY: restart-ms
restart-ms: build start

.PHONY: logs
logs:
	docker-compose -p ${project} logs -f

.PHONY: logs-db
logs-db:
	docker-compose -p ${project} logs -f ms-reservations-redis

.PHONY: logs-app
logs-app:
	docker-compose -p ${project} logs -f ${service}

.PHONY: shell
shell:
	docker-compose -p ${project} exec ${service} bash

.PHONY: build
build:
	./gradlew installDist
	docker-compose -p ${project} build --no-cache

.PHONY: clean
clean: stop build start

.PHONY: test
test:
	./gradlew test

