
# Tools
- VSCode
    - Git Graph 
    - Language Support for Java by Red Hat
    - Debugger for Java
    - Test Runner for Java
    - Maven for Java
    - IntelliCode
- Node.js https://nodejs.org/dist/v22.11.0/node-v22.11.0-x64.msi

#  Build
mvn clean install
mvnw package && java -jar target/springboot-0.0.1.jar

# Docker Compose

docker-compose build

docker-compose up --build

docker-compose ps

docker-compose down

# Docker

Using Docker VSCode Extension

## Backend

F1 or strg+shift+p -> Docker Images: Build Image
docker build --pull --rm -f "backend\Dockerfile" -t ovguccd:backend "backend"

For specific Docker Version (ovguccd:backend):
docker run -p 8080:8080 ovguccd::backend "backend"

If latest Docker Version ovgucc:latest exists:
docker run -p 8080:8080 backend

## Frontend

docker run -p 3000:3000 ovguccd:frontend

## Stop Docker

Running Containers:
docker ps
only ids:
docker ps -q

strg+shift+p -> Docker -> Stop -> select containers

docker kill $(docker ps -q)

# Branching Strategy

## Create your local working branch from develop
VSCode: Ctrl+Shift+P: Create Branch from develop

(Just create branch if you already made some changes)

## Work on your branch
- A lot of small commits are possible
- Maybe combine some commits before rebasing to make it easier

## Make a pull request
- Fetch the current state of devel
- Rebase your branch on devel
- Make a pull request from your branch to devel
    - Make sure you assign someone for the review

