# pricecalulator

[![build](https://github.com/Priyadoaba/pricecalculator/actions/workflows/ci.yml/badge.svg)](https://github.com/priyadoaba/Pricecalulator/actions/workflows/ci.yml)

The application is based on spring boot framework 
and is a simple price calculator for a watch cart based on pre-populated watch details with or without discount.

Pre-populated watch details are as follows:

| Watch ID | Watch Name| Unit Price| Discount|
|----------|---|---|---|
| 001      | Rolex| 100| 3 for 200|
| 002      | Michael Kors| 80| 2 for 120|
| 003      | Swatch| 50||
| 004      | Casio| 30||

## Considerations

- There is at most one discount per watch
- Price of the watch is always a positive whole number


## Requirements

For building and running the application you need:

- [JDK 22](https://www.oracle.com/java/technologies/downloads/#java22)
- [Maven 3](https://maven.apache.org)


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 

- One way is to execute the `main` method in the `no.sample.pricecalculator.PriceCalculatorApplication` class from your IDE.

- Running executable
    ```shell
    mvn clean package
   
    java -jar target/pricecalculator.jar
    ```

- you can also use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

    ```shell
    mvn spring-boot:run
  
- Build and run the application using docker
    ```shell
    mvn clean package
    docker build -t price-calculator .
    docker run --rm -p 8080:8080 -it price-calculator
    ```

- Build and run the application using docker-compose
    ```shell
    mvn clean package
    docker compose up --build
    docker compose down --rmi all
    ```


## Rest API

### Get price of cart

#### Request

`POST /checkout`

    curl -i -H 'Accept: application/json' -H 'Content-Type: application/json' -X POST -d '["001", "002", "003"]' http://localhost:8080/checkout

#### Response

    HTTP/1.1 200 
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Fri, 31 May 2024 13:40:58 GMT

    {"price":230}

## Api Docs

Swagger is disabled by default. To enable swagger set the following property in application.yml

```yaml
springdoc:
  swagger-ui:
    enabled: true
```       

api docs is available at http://localhost:8080/v3/api-docs

Swagger documentation is available at http://localhost:8080/swagger-ui.html

## Monitoring

Prometheus and Grafana is enabled on dev profile with docker compose setup.
access prometheus at http://localhost:9090
access grafana at http://localhost:3000


