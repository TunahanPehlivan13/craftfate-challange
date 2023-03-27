# craftfate-challange

## About the Project
We built a simple food order application with MySql and Spring boot and two services. Services communicate via Rest each other and
connect only one database.

## Getting Started
Follow the instructions to set up the project in your local environment.

### Prerequisites
* JDK 11
* Docker

### Installation
Please run the command in the project's root folder where placed `docker-compose.yml`.

#### Run
Use `docker-compose up --build` command to launch all services. You may encounter a memory leak or some system issue
due to consume too much system resource. If this happens you can make passive some lines in the `docker-compose.yml`.
