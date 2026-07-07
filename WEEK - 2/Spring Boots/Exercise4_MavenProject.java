// ============================================================================
// Exercise 4: Creating and Configuring a Maven Project
// Project: LibraryManagement
// ============================================================================
//
// What we're doing here:
// We're building a simple Library Management app using Maven and Spring.
// The idea is to understand how Maven manages our project dependencies
// and how Spring wires everything together using XML configuration.
//
// Here's how the project is organized:
//
// LibraryManagement/
// ├── pom.xml                          --> Our Maven config (dependencies, plugins)
// └── src/
//     └── main/
//         ├── java/
//         │   └── com/library/
//         │       ├── Main.java              --> Starting point of the app
//         │       ├── service/
//         │       │   └── BookService.java   --> Handles book-related logic
//         │       └── repository/
//         │           └── BookRepository.java --> Talks to the data layer
//         └── resources/
//             └── applicationContext.xml      --> Spring bean definitions
//
// ============================================================================


// ============================================================================
// FILE 1: pom.xml
// This is the heart of any Maven project. It tells Maven what libraries
// we need and how to compile our code.
// ============================================================================
//
// <?xml version="1.0" encoding="UTF-8"?>
// <project xmlns="http://maven.apache.org/POM/4.0.0"
//          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
//                              http://maven.apache.org/xsd/maven-4.0.0.xsd">
//     <modelVersion>4.0.0</modelVersion>
//
//     <!-- Basic info about our project -->
//     <groupId>com.library</groupId>
//     <artifactId>LibraryManagement</artifactId>
//     <version>1.0-SNAPSHOT</version>
//     <packaging>war</packaging>    <!-- We're using 'war' because we have Spring WebMVC -->
//
//     <properties>
//         <!-- We're targeting Java 1.8 as required by the exercise -->
//         <maven.compiler.source>1.8</maven.compiler.source>
//         <maven.compiler.target>1.8</maven.compiler.target>
//
//         <!-- Keeping the Spring version in one place so it's easy to update later -->
//         <spring.version>5.3.30</spring.version>
//     </properties>
//
//     <dependencies>
//         <!-- Spring Core: The foundation — provides basic framework utilities -->
//         <dependency>
//             <groupId>org.springframework</groupId>
//             <artifactId>spring-core</artifactId>
//             <version>${spring.version}</version>
//         </dependency>
//
//         <!-- Spring Context: This gives us the ApplicationContext and
//              dependency injection features we use to wire beans together -->
//         <dependency>
//             <groupId>org.springframework</groupId>
//             <artifactId>spring-context</artifactId>
//             <version>${spring.version}</version>
//         </dependency>
//
//         <!-- Spring AOP: Allows us to use Aspect-Oriented Programming,
//              useful for things like logging and transaction management -->
//         <dependency>
//             <groupId>org.springframework</groupId>
//             <artifactId>spring-aop</artifactId>
//             <version>${spring.version}</version>
//         </dependency>
//
//         <!-- Spring WebMVC: Needed if we want to build web pages or REST APIs
//              using Spring's MVC pattern (DispatcherServlet, Controllers, etc.) -->
//         <dependency>
//             <groupId>org.springframework</groupId>
//             <artifactId>spring-webmvc</artifactId>
//             <version>${spring.version}</version>
//         </dependency>
//     </dependencies>
//
//     <build>
//         <plugins>
//             <!-- We're explicitly telling Maven to compile our code with Java 1.8 -->
//             <plugin>
//                 <groupId>org.apache.maven.plugins</groupId>
//                 <artifactId>maven-compiler-plugin</artifactId>
//                 <version>3.11.0</version>
//                 <configuration>
//                     <source>1.8</source>
//                     <target>1.8</target>
//                 </configuration>
//             </plugin>
//         </plugins>
//     </build>
// </project>
//
// ============================================================================


// ============================================================================
// FILE 2: Main.java
// This is where everything kicks off. We load up the Spring container
// and ask it to give us the BookService bean so we can test things out.
// ============================================================================

package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

class Main {
    public static void main(String[] args) {
        // First, we load our Spring configuration from the XML file.
        // This creates all the beans defined in applicationContext.xml.
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Now we ask Spring to give us the BookService bean.
        // Spring has already injected BookRepository into it for us!
        BookService bookService = (BookService) context.getBean("bookService");

        // Let's see if everything is wired up correctly.
        bookService.manageBooks();
    }
}


// ============================================================================
// FILE 3: BookService.java
// This is our service layer. It handles the business logic for books.
// Spring injects the BookRepository into this class through the setter method.
// ============================================================================

// package com.library.service;
//
// import com.library.repository.BookRepository;

class BookService {
    private BookRepository bookRepository;

    // Spring uses this setter to inject the BookRepository bean.
    // In applicationContext.xml, we told Spring to do this using the <property> tag.
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // This method demonstrates that our dependency injection is working.
    // If Spring injected the repository correctly, it will fetch books.
    // Otherwise, we'll see a warning message.
    public void manageBooks() {
        System.out.println("Managing books in BookService...");
        if (bookRepository != null) {
            bookRepository.getBooks();
        } else {
            System.out.println("BookRepository is not initialized!");
        }
    }
}


// ============================================================================
// FILE 4: BookRepository.java
// This represents our data access layer. In a real app, this would
// connect to a database. For now, it just prints a message to show
// that it's been called successfully.
// ============================================================================

// package com.library.repository;

class BookRepository {
    public void getBooks() {
        System.out.println("Fetching books from BookRepository...");
    }
}


// ============================================================================
// FILE 5: applicationContext.xml
// This is our Spring configuration file. We define our beans here and
// tell Spring how to wire them together. Think of it as a recipe that
// Spring follows to create and connect all the objects our app needs.
// ============================================================================
//
// <?xml version="1.0" encoding="UTF-8"?>
// <beans xmlns="http://www.springframework.org/schema/beans"
//        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//        xsi:schemaLocation="http://www.springframework.org/schema/beans
//                            http://www.springframework.org/schema/beans/spring-beans.xsd">
//
//     <!-- First, we create the BookRepository bean.
//          Spring will instantiate this class for us. -->
//     <bean id="bookRepository" class="com.library.repository.BookRepository"/>
//
//     <!-- Next, we create the BookService bean and inject
//          the bookRepository into it using setter injection.
//          The 'ref' attribute points to the bean we defined above. -->
//     <bean id="bookService" class="com.library.service.BookService">
//         <property name="bookRepository" ref="bookRepository"/>
//     </bean>
//
// </beans>
//
// ============================================================================


// ============================================================================
// Quick Recap — What we did in Exercise 4:
// ============================================================================
//
// 1. Created a Maven Project called "LibraryManagement"
//    - groupId:    com.library
//    - artifactId: LibraryManagement
//    - packaging:  war (since we're using Spring WebMVC for web apps)
//
// 2. Added the Spring dependencies our project needs:
//    - spring-core    → The base framework that everything else builds on
//    - spring-context → Gives us the DI container and ApplicationContext
//    - spring-aop     → For aspect-oriented programming (logging, security, etc.)
//    - spring-webmvc  → For building web applications with MVC pattern
//    (All using version 5.3.30)
//
// 3. Configured the Maven Compiler Plugin for Java 1.8:
//    - Set it in <properties> so Maven knows globally
//    - Also added the plugin explicitly in <build> for full control
//
// ============================================================================
