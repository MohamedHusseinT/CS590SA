package books.BookApplication.service;
import books.BookApplication.repository.*;
import books.BookApplication.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.jms.core.JmsTemplate;


@Service
public class BookService {
    private final BookRepository repo;
    private final JmsTemplate jms;

    public BookService(BookRepository repo, JmsTemplate jms)
    {
        this.repo = repo;
        this.jms  = jms;

    }

    public void addBook(Book b)
    {
        repo.add(b);
        jms.convertAndSend("bookQueue", b);

    }

    public void updateBook(Book b)
    {
        repo.update(b);
        jms.convertAndSend("bookQueue", b);
    }

    public void deleteBook(String isbn)
    {
        Book b = repo.findByIsbn(isbn);
        repo.delete(isbn);
        jms.convertAndSend("bookQueue", b);
    }

    public Book getBook(String isbn)
    {
        return repo.findByIsbn(isbn);
    }

    public List<Book> getAllBooks()
    {
        return new ArrayList<>(repo.findAll());
    }
}
