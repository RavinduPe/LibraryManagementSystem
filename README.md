# 📚 Library Management System - Backend

This is the backend API for the Library Management System built using Spring Boot.  
It provides authentication, role-based authorization, and full library management features including book borrowing with business rules.

---

## 🚀 Technologies Used

- Java 8
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok

---

## 📂 Project Structure

```
com.example.LibraryManagementSystem
│
├── controller        # REST Controllers (API endpoints)
├── service           # Business Logic layer
├── repository        # JPA Repositories (Database access)
├── entity            # Database Entities
├── dto               # Data Transfer Objects
├── config            # Application & general configurations
├── security          # JWT, filters, authentication, authorization
├── exception         # Custom exceptions & global exception handling
├── enums             # Application enums (Role, Status, etc.)
```

---

## 🔐 Authentication & Authorization

The system uses **Spring Security with JWT**.

### Roles:
- `ADMIN`
- `USER`

### Access Rules:
- ADMIN → Full access (manage books, authors, users)
- USER → Can borrow and return books
- Borrowing endpoints are protected with:
  ```
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  ```

---

## 👤 User (Member)

In this system:

`User` acts as the library member.

There is no separate Member borrowing logic.  
All borrow operations are linked to the authenticated `User`.

---

## 📚 Features

### ✅ Author Management
- Create Author
- Update Author
- Delete Author
- Get All Authors

### ✅ Book Management
- Add Book
- Update Book
- Delete Book
- Get All Books
- Track available copies

### ✅ Borrowing Management

Business Rules:

1. A user can borrow **maximum 5 books** at a time.
2. Each borrowed book must be returned within **7 days**.
3. A book cannot be borrowed if `availableCopies = 0`.
4. Same copy cannot be borrowed by multiple users simultaneously.

---

## 🔄 Borrowing Flow

### Borrow Book
**Endpoint**
```
POST /api/borrow/{userId}/{bookId}
```

**Process**
- Check user exists
- Check book exists
- Check user has less than 5 active borrowings
- Check book availability
- Create borrowing record
- Set due date = borrowDate + 7 days
- Decrease availableCopies

---

### Return Book
**Endpoint**
```
PUT /api/borrow/return/{borrowingId}
```

**Process**
- Check borrowing record exists
- Ensure book is not already returned
- Mark as returned
- Increase availableCopies

---

## 🗄 Database Entities

### User
- id
- username
- password
- role

### Author
- id
- name

### Book
- id
- title
- totalCopies
- availableCopies
- author

### Borrowing
- id
- borrowDate
- dueDate
- returned
- user (ManyToOne)
- book (ManyToOne)

---

## 🧠 Borrowing Logic (Service Layer)

- `countByUserAndReturnedFalse(user)` ensures max 5 books
- Due date automatically set using:
  ```java
  LocalDate.now().plusDays(7);
  ```

---

## 🛠 How to Run the Project

### 1️⃣ Clone Repository
```
git clone <your-repo-url>
```

### 2️⃣ Configure Database
Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run Application
```
mvn spring-boot:run
```

Server runs on:
```
http://localhost:8080
```

---

## 🧪 Testing with Postman

1. Login and get JWT token.
2. Add token to Authorization header:

```
Authorization: Bearer <your_token>
```

3. Test borrow and return endpoints.

---

## 📌 Future Improvements

- Late return penalty system
- Borrow history endpoint
- Pagination & filtering
- Exception handling with @ControllerAdvice
- Swagger documentation

---

## 📄 License

This project is for educational purposes.

---

## 👨‍💻 Author

Developed as a Spring Boot practice project.
