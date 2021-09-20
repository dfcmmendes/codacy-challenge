# codacy-challenge Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

If you have Maven (3.8.2 or above) You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev

#or using the Makefile
make dev-mode
```

If you have Maven (3.8.2 or above) and Docker you can use the Makefile available:
```shell script
#To package and launch the docker container with the app running
make start-all
#To stop the container
make stop-all
```

It also possible to just use the Dockerfile (also requires Maven 3.8.2 or above and Docker):
```shell script
#Package the app
./mvnw package

#Build the docker image
docker build -f src/main/docker/Dockerfile -t quarkus/codacy-challenge .

#Run the container
docker run -i --rm -p 8080:8080 quarkus/codacy-challenge
```

There is a Swagger in the Quarkus Dev UI!
> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

# Request

The available request is: [http://{{host}}:{{port}}/codacy/log/{owner}/{repository}](http://{host}:{8080}/codacy/log/{owner}/{repository})
