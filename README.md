# College Library Management System

A simple and secure **Library Management System** built using **Java 17, Spring Boot, Spring Security, JWT and MySQL**.

## 🛠 Technologies

* Java 17
* Spring Boot
* Spring Security
* JWT
* Google OAuth2
* Spring Data JPA / Hibernate
* MySQL
* Maven
* BCrypt

## 📁 Project Structure

```text
src/main/java/com/example/library
│
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

## 🔐 Authentication

The system supports:

* User Registration
* User Login
* JWT Access Token
* Refresh Token
* Refresh Token Rotation
* Logout
* Google OAuth2 Login
* Password Recovery
* BCrypt Password Encryption

## 👥 Roles

### USER

* View books
* Borrow books
* Return books
* View borrowing history
* View active books
* View fines

### ADMIN

* Add books
* Update books
* Delete books
* View users
* View borrowings
* View fines
* Update borrowing/fine details

## 📚 Borrowing

A book can be borrowed for **5 days**.

```text
Borrow Book
    ↓
Due Date = Borrow Date + 5 Days
    ↓
Return Book
    ↓
Check Late Days
    ↓
Fine = Late Days × ₹10
```

Example:

```text
Due Date:     10 Sep
Return Date:  12 Sep

Late Days = 2
Fine = ₹20
```

## 🔑 JWT Flow

```text
Login
  ↓
Access Token + Refresh Token
  ↓
Send Access Token
  ↓
JWT Filter
  ↓
Validate Token
  ↓
Check Role
  ↓
Allow Request
```

Access token:

```text
15 minutes
```

Refresh token:

```text
7 days
```

Refresh tokens are rotated when they are used.

## 🌐 Main APIs

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout
POST /api/auth/forgot-password
POST /api/auth/reset-password
```

### Books

```http
GET /api/books
GET /api/books/{id}
```

### Borrowing

```http
POST /api/borrow/book/{bookId}
PUT /api/borrow/{transactionId}/return
GET /api/borrow/my-history
GET /api/borrow/my-active
GET /api/borrow/my-fines
```

### Admin

```http
POST /api/admin/books
PUT /api/admin/books/{id}
DELETE /api/admin/books/{id}

GET /api/admin/users
GET /api/admin/users/{id}

GET /api/admin/borrowings
GET /api/admin/borrowings/{id}

GET /api/admin/fines
PUT /api/admin/borrowings/{id}
```

## 🗄️ Database

Create the MySQL database:

```sql
CREATE DATABASE library_db;
```

Configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=library_user
spring.datasource.password=your-password
```

Main tables:

```text
users
books
borrow_transactions
refresh_tokens
password_reset_tokens
```

## ▶️ Run the Project

Make sure you have:

```text
Java 17
MySQL
Maven
```

Then run:

```bash
mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

## 🧪 Postman Testing

Recommended order:

```text
1. Register
2. Login
3. Copy Access Token
4. Login as Admin
5. Create Book
6. Get Books
7. Borrow Book
8. Check My Active Books
9. Return Book
10. Check Fine
11. Refresh Token
12. Logout
13. Test Password Recovery
14. Test Google Login
15. Test USER → ADMIN access
```

For protected APIs:

```http
Authorization: Bearer <accessToken>
```

## 🔒 Security

* Passwords are stored using BCrypt.
* JWT protects authenticated APIs.
* ADMIN APIs require `ROLE_ADMIN`.
* USER cannot access ADMIN APIs.
* Users can access only their own borrowing information.
* Refresh tokens are stored and revocable.
* Password reset tokens expire.

## 🔄 Overall Flow

```text
Client
  ↓
Authentication
  ↓
JWT
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
MySQL
```

### Library Flow

```text
Admin → Add Book
          ↓
       Available
          ↓
User → Borrow
          ↓
      5 Days
          ↓
User → Return
          ↓
   Calculate Fine
          ↓
      ₹10 / Day
```

## ✅ Features

* Secure Authentication
* JWT Authorization
* Google Login
* Role-Based Access
* Book Management
* Borrow & Return
* Automatic Fine Calculation
* Password Recovery
* MySQL Database
* REST APIs
* Postman Testing

# 👨‍💻 Project

**College Library Management System**

Built with **Spring Boot + Spring Security + JWT + MySQL**.
