package books.BookApplication.controller;

import books.BookApplication.domain.*;
import books.BookApplication.service.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService svc;
    public BookController(BookService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book b) {
        svc.addBook(b);
        return ResponseEntity.ok(b);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book b) {
        svc.updateBook(b);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        svc.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable String isbn) {
        Book b = svc.getBook(isbn);
        if (b == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(b);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(svc.getAllBooks());
    }


}
