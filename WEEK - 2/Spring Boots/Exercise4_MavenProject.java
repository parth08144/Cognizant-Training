package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = (BookService) context.getBean("bookService");

        bookService.manageBooks();
    }
}

class BookService {
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void manageBooks() {
        System.out.println("Managing books in BookService...");
        if (bookRepository != null) {
            bookRepository.getBooks();
        } else {
            System.out.println("BookRepository is not initialized!");
        }
    }
}

class BookRepository {
    public void getBooks() {
        System.out.println("Fetching books from BookRepository...");
    }
}
