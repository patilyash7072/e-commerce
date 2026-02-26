# 🛒 E-Commerce Application

A simple Java-based E-Commerce application built with Maven and structured logging support.

## 📌 Overview

This project is a core Java e-commerce application designed to demonstrate:

- Clean project structure

- Modern logging setup (SLF4J 2.x + Log4j2)

- Maven-based build management

- Java 25 configuration

- Console logging with color support (Jansi)

### It can serve as a foundation for building:

- Product management systems

- Shopping cart logic

- Order processing systems

- Console-based commerce simulations

- Backend business logic prototypes

## 🏗️ Tech Stack

- Language	Java 25

- Build Tool	Maven

- Logging API	SLF4J 2.x

- Logging Engine	Log4j2

- Console Colors	Jansi

## 📦 Project Configuration

Logging Dependencies

- slf4j-api (Logging API)

- log4j-api

- log4j-core

- log4j-slf4j2-impl (Correct SLF4J 2.x binding)

- jansi (Colored console output support)

## 🚀 Getting Started

### ✅ Prerequisites

- Java 25 installed

- Maven installed

- Git installed

🔹 Clone the Repository

```git clone https://github.com/patilyash7072/e-commerce.git```

```cd e-commerce```

### 🔹 Build the Project

```mvn clean install```

### 🔹 Run the Application

If you have a main class:

```mvn exec:java```

Or run directly from your IDE.

## 🧾 Logging Configuration

This project uses:

- SLF4J 2.x as logging abstraction

- Log4j2 as logging implementation

- Jansi for colored logs (especially on Windows)

## 📁 Project Structure

src/  
 ├── main/  
 │   ├── java/com/dss/  
 │   │   ├── model/  
 │   │   ├── service/  
 │   │   ├── util/  
 │   │   └── Main.java  
 │   └── resources/  
 │       └── log4j2.xml  
 └── test/  
 
## 🛠️ Possible Future Enhancements

- Implement REST API layer

- Add Spring Boot support

- Add authentication & authorization

- Add unit testing framework (JUnit 5)

## 🧪 Testing

To run tests:

```mvn test```

(Ensure test dependencies are added if not already present.)

## 📄 License

This project is currently unlicensed.


## 👨‍💻 Author

Yash Patil  

Developed as a Java-based e-commerce foundation project.
