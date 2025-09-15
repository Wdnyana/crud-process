# REST API - CRUD Shipment/Delivery Process

This is a simple REST API project built with Quarkus and Java Corretto 21 to manage the CRUD (Create, Read, Update, Delete) process for shipment data. The project covers the logic for handling shipments, customer management, products (including stock), and the shipment records themselves, while also implementing basic business logic.

## ✨ Features
- Customer Management: Full CRUD operations for customer data.

- Product Management: Full CRUD operations for product data, including stock information.

- Shipment Management: Create, read, update status, and delete shipment data.

- Business Logic: Product stock is automatically decremented when a new shipment is successfully created.

- Layered Architecture: Implements a clear separation of concerns between the Resource (Controller), Service (Business Logic), and Repository (Data Access) layers.

- Automatic API Documentation: Automatically generates interactive API documentation using OpenAPI and Swagger UI.

## 🛠️ Technology
- Framework: Quarkus 3.26.3

- Language: Java Corretto 21

- Build Tool: Maven

- Database: PostgreSQL

- Data Access: Hibernate ORM (JPA)

- API: JAX-RS (RESTEasy Reactive)

- Documentation: OpenAPI (SmallRye) & Swagger UI

## ⚙️ Prerequisites
Before you begin, ensure you have the following software installed on your machine:

- JDK 21 or newer

- Apache Maven

- Docker and Docker Compose

## 🚀 Installation and Configuration
Follow these steps to set up the project in your local environment.

### 1. Clone the Repository 
```
git clone https://github.com/Wdnyana/crud-process 
cd crud_process
```

### 2. Configure Environment Variables
This project uses a ```.env``` file to store configurations used by the Quarkus application and Docker Compose.

#### A. Copy the example file:
```
cp .env.example .env
```

#### B. Open the .env file and adjust the values. The password here must match the one that will be used by the database.
```
QUARKUS_DATASOURCE_NAME_JDBC_URL=jdbc:postgresql://localhost:[yourport]/databasename
QUARKUS_DATASOURCE_USERNAME=yourusername
QUARKUS_DATASOURCE_PASSWORD=yourpassword
```

### 3. Run the PostgreSQL Database
This project uses Docker Compose to simplify the database setup. Simply run the following command from the project's root directory:
```
docker compose up -d 
// OR
sudo docker compose up -d
// OR
docker-compose up -d
```
This command will read the docker-compose.yml file, pull the PostgreSQL image, and run the container in the background (-d).

The database configuration (such as the user and password) is automatically sourced from your .env file.

Data will be persisted in a Docker volume named postgres_data to prevent data loss when the container is stopped.

## ▶️  Packaging and running the application in dev mode
You can run your application in dev mode that enables live coding using:


```shell script
./mvnw quarkus:dev
```

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/crud-process-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## 📖 API Documentation and Testing

This project automatically generates interactive API documentation using Swagger UI. This is the primary way to view and test all available endpoints.

1. Ensure your application is running.

2. Open your browser and navigate to: http://localhost:8080/q/swagger-ui

From this page, you can:

- View a list of all available endpoints.

- See the details of each endpoint (HTTP method, parameters, expected body).

- Run and test each endpoint directly from the browser.

### API Endpoints
| Method     |             Endpoint             |                                              Description |
|:-----------|:--------------------------------:|---------------------------------------------------------:|
| **POST**   |       ```/api/customer```        |                                   Create a new customer. |
| **GET**    |    ```/api/customer/gets ```     |                                        Get All Customers |
| **GET**    |     ```/api/customer{id} ```     |                                       Get Customer by ID |
| **PUT**    |    ```/api/customer/{id} ```     |                                     Update data Customer |
| **DELETE** |   ```  /api/customer/{id} ```    |                                    Delete Customer by ID |
| **POST**   |        ```/api/product```        |                                    Create a new product. |
| **GET**    |     ```/api/product/gets ```     |                                         Get All Products |
| **GET**    |     ```/api/product{id} ```      |                                        Get Product by ID |
| **PUT**    |     ```/api/product/{id} ```     |                                      Update Data Product |
| **DELETE** |    ```/api/product/{id}  ```     |                                     Delete Product by ID |
| **POST**   |       ```/api/shipment```        |                                   Create a new shipment. |
| **GET**    | ```/api/shipment/gets-summary``` |                                        Get All shipments |
| **GET**    |    ```/api/shipment{id}  ```     |                                       Get Shipment by ID |
| **PUT**    | ```/api/shipment/{id}/status ``` |    Update status shipment if shipment already to shipped |
| **DELETE** |    ```/api/shipment/{id} ```     |                                    Delete Shipment by ID |

### 🏗️ Project Structure
The project follows a layered architecture to ensure a clear separation of concerns:
- ```src/main/java/org/purwa/crud_process/data```: DTO Layer - Contains objects for data transfer between the client and the API.
- ```src/main/java/org/purwa/crud_process/enums```: Enums Layer - Contains for Data Type Enums.
- ```src/main/java/org/purwa/crud_process/model```:Model Layer - Contains JPA Entity classes and Enums.
- ```src/main/java/org/purwa/crud_process/repository``` Repository Layer - Responsible for data access to the database.
- ```src/main/java/org/purwa/crud_process/resource```: Resource Layer (Controller) - Handles HTTP requests/responses.
- ```src/main/java/org/purwa/crud_process/service```: Service Layer - Contains all business logic.