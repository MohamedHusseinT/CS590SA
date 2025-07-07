package BookClient;

import BookClient.domain.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;

@SpringBootApplication
public class BookClientApplication implements CommandLineRunner{

    private final RestOperations rest;

    public BookClientApplication(RestOperations rest) {
        this.rest = rest;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookClientApplication.class, args);
    }

    @Bean
    public static RestOperations restTemplate(RestTemplateBuilder b) {
        return b.build();
    }

    @Override
    public void run(String... args) throws Exception {
        String base = "http://localhost:8080/books";

        // 1) Create a book
        Book newBook = new Book("5555", "Jane Doe", "Spring in Action", 39.99);
        ResponseEntity<Void> created = rest.postForEntity(base, newBook, Void.class);
        System.out.println("POST /books → " + created.getStatusCode());

        // 2) Fetch it back
        Book fetched = rest.getForObject(base + "/5555", Book.class);
        System.out.println("GET /books/5555 → " + fetched);

        // 3) Update price
        fetched.setPrice(35.00);
        rest.put(base, fetched);
        System.out.println("PUT /books → updated price to 35.00");

        // 4) List all books
        Book[] all = rest.getForObject(base, Book[].class);
        System.out.println("GET /books → " + Arrays.toString(all));

        // 5) Delete the book
        rest.delete(base + "/5555");
        System.out.println("DELETE /books/5555 → done");

        // 6) Confirm deletion (should 404)
        try {
            rest.getForObject(base + "/5555", Book.class);
        } catch (Exception ex) {
            System.out.println("GET /books/5555 after delete → " + ex.getMessage());
        }

        // Exit after running
        System.exit(0);
    }
}
