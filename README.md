# Three Person Chess

Java Springboot backend-driven React Webapp

![grafik](https://github.com/user-attachments/assets/37ec9558-dfcc-49c9-83e7-b4760d559ac7)


## Requirements

## Run

Backend only:

    mvn spring-boot:run

Frontend only:

    npm start

Full stack:

    docker-compose up

# Test Coverage

[JaCoCo Report](https://ovgutasks.github.io/ovgu_CCD/index.html)

# Doxy Documentation

[Doxy Documentation](https://ovgutasks.github.io/ovgu_CCD/doxygen/annotated.html)



# Tools
- VSCode
    - Git Graph 
    - Language Support for Java by Red Hat
    - Debugger for Java
    - Test Runner for Java
    - Maven for Java
    - IntelliCode
- Node.js https://nodejs.org/dist/v22.11.0/node-v22.11.0-x64.msi

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

