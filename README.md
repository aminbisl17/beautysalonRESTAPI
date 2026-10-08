[README.md](https://github.com/user-attachments/files/33213868/README.md)
# Beauty Salon REST API

A Spring Boot REST API for managing beauty salon operations, including authentication, client management, employee management, booking-related workflows, notifications, and media storage.

This project is built with Java and Spring Boot, with SQL Server as the main persistence layer and Swagger/OpenAPI for API documentation.

## Features

- JWT-based authentication and authorization
- Client-side and admin-side API endpoints
- Employee and tenant-related services
- SQL Server database integration using Spring Data JPA
- Email notifications via Spring Mail
- SMS notifications via Twilio
- Firebase integration for push/notification support
- Azure Blob Storage support for uploaded images/files
- Swagger UI and OpenAPI documentation

## Tech Stack

- Java 25
- Spring Boot 4.0.0
- Spring Web
- Spring Security
- Spring Data JPA
- SQL Server
- Maven
- JWT (`jjwt`)
- Swagger / OpenAPI (`springdoc-openapi-starter-webmvc-ui`)
- Azure Blob Storage SDK
- Twilio SDK
- Firebase Admin SDK
- Lombok

## Project Structure

```text
beautysalonRESTAPI/
├── .github/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/beautysalonRESTAPI/
│   │   │       ├── api/
│   │   │       ├── Configuration/
│   │   │       ├── dto/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       ├── sqlfiles/
│   │   │       └── BeautysalonRestapiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── SherbimetImgPath/
│   └── test/
├── .gitattributes
├── .gitignore
├── Beauty-Salon.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Prerequisites

Before running this project, make sure you have:

- Java 25 or a compatible JDK installed
- Maven installed
- SQL Server running and accessible
- Azure Storage account configured
- SMTP mail credentials
- Twilio credentials
- Firebase configuration (if used by your app)
- Optional: IDE such as IntelliJ IDEA or VS Code

## Configuration

The application configuration is managed in:

- `src/main/resources/application.properties`

### Required environment variables

Set the following environment variables before starting the app:

```bash
DB_URL=jdbc:sqlserver://<host>:<port>;databaseName=<database>
DB_USERNAME=<your_db_username>
DB_PASSWORD=<your_db_password>

MAIL_HOST=<smtp_host>
MAIL_USERNAME=<smtp_username>
MAIL_PASSWORD=<smtp_password>

AZURE_STORAGE_CONNECTION_STRING=<your_azure_storage_connection_string>
```

You may also need additional runtime secrets depending on your implementation of:
- JWT signing values
- Firebase configuration
- Twilio credentials

## Running the Application

Clone the repository:

```bash
git clone https://github.com/aminbisl17/beautysalonRESTAPI.git
cd beautysalonRESTAPI
```

Build the project:

```bash
./mvnw clean package
```

Run the application:

```bash
./mvnw spring-boot:run
```

Or run the packaged jar:

```bash
java -jar target/beautysalonRESTAPI-0.0.1-SNAPSHOT.jar
```

The application runs on:

- Host: `0.0.0.0`
- Port: `8000`

## API Documentation

This project includes SpringDoc OpenAPI support.

Once the app is running, you can access:

- Swagger UI: `http://localhost:8000/swagger-ui.html`
- OpenAPI JSON/YAML: `http://localhost:8000/v3/api-docs`

## Notes

- The project uses `spring.jpa.hibernate.ddl-auto=none`, so database schema management is expected to be handled externally or through SQL scripts.
- The app is designed for a beauty salon management context and includes support for notifications, media storage, and account/security flows.
- This repository appears to be an active learning/prototype project with real-world backend patterns, but it should be reviewed carefully before production deployment.

## License

This project does not currently show an explicit license file in the repository.

## Contact

For questions or collaboration:

- GitHub: https://github.com/aminbisl17
- Repository: https://github.com/aminbisl17/beautysalonRESTAPI
