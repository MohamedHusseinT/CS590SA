package books.BookApplication.repository;

import books.BookApplication.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {
    private final Map<String, Book> store = new HashMap<>();

    public void add(Book b) {
        store.put(b.getIsbn(), b);
    }

    public void update(Book b) {
        store.put(b.getIsbn(), b);
    }

    public void delete(String isbn) {
        store.remove(isbn);
    }

    public Book findByIsbn(String isbn) {
        return store.get(isbn);
    }

    public Collection<Book> findAll() {
        return store.values();
    }
}
