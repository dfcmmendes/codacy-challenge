dev-mode:
	mvn clean compile quarkus:dev

test:
	mvn clean install

package:
	mvn package -DskipTests

start-all:
	package docker-compose -f src/main/docker/docker-compose.yml up -d --build

stop-all:
	docker-compose -f src/main/docker/docker-compose.yml down