# Library Management System

A web-based library management system built with Spring MVC, JDBC, and Thymeleaf.

## Features

### User Roles
- **Reader**: Browse books, place requests, view order history
- **Librarian**: Process book lending/returns, manage orders
- **Administrator**: Manage users and book catalog

### Key Functionality
- User registration and authentication
- Book catalog with search and filtering
- Book request system (Home lending / Reading room)
- Order management with status tracking
- Multi-language support (English/Russian)
- Responsive design with Bootstrap

## Technology Stack

- **Backend**: Java 17, Spring MVC, Spring Core
- **Database**: MySQL with custom JDBC implementation
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Build Tool**: Maven
- **Server**: Apache Tomcat 11
- **Logging**: SLF4J with Log4j2

## Architecture

The application follows a layered architecture:
- **Controller Layer**: Spring MVC controllers
- **Service Layer**: Business logic implementation
- **DAO Layer**: Data access with JDBC
- **Model Layer**: Domain entities

### Design Patterns Used
1. **Singleton**: ConnectionPool implementation
2. **Builder**: Order object construction
3. **DAO Pattern**: Data access abstraction
4. **MVC Pattern**: Overall application structure

## Prerequisites

- JDK 17 or higher
- MySQL 8.0 or higher
- Apache Tomcat 11
- Maven 3.6+

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

2. Create MySQL database:
```sql
CREATE DATABASE library CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Configure database connection in `src/main/resources/application.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
db.username=your_username
db.password=your_password
```

4. Initialize database schema:
```bash
mysql -u your_username -p library < src/main/resources/db/schema.sql
mysql -u your_username -p library < src/main/resources/db/data.sql
```

5. Build the project:
```bash
mvn clean package
```

6. Deploy to Tomcat:
- Copy `target/library.war` to Tomcat's `webapps` directory
- Start Tomcat

7. Access the application at: `http://localhost:8080/library`

## Default Users

| Email | Password | Role |
|-------|----------|------|
| admin@library.com | admin123 | Admin |
| librarian@library.com | lib123 | Librarian |
| reader1@example.com | reader123 | Reader |

## Project Structure

```
src/
├── main/
│   ├── java/com/library/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # Spring MVC controllers
│   │   ├── dao/           # Data access objects
│   │   ├── model/         # Domain models
│   │   ├── service/       # Business logic
│   │   ├── interceptor/   # Request interceptors
│   │   ├── util/          # Utility classes
│   │   └── exception/     # Custom exceptions
│   ├── resources/
│   │   ├── application.properties
│   │   ├── messages*.properties  # i18n files
│   │   ├── log4j2.xml
│   │   └── db/            # SQL scripts
│   └── webapp/
│       ├── WEB-INF/views/ # Thymeleaf templates
│       └── static/        # CSS, JS files
└── test/                  # Unit tests
```

## Testing

Run unit tests:
```bash
mvn test
```

Generate test coverage report:
```bash
mvn jacoco:report
```

## Security Features

- BCrypt password hashing
- Session-based authentication
- CSRF protection
- Input validation and sanitization
- Role-based access control

## Internationalization

The application supports:
- English (default)
- Russian

Switch languages using the dropdown in the navigation bar.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.