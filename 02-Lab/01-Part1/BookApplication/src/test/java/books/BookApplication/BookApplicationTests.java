package books.BookApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import books.BookApplication.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BookApplicationTests {

	//@Test
	//void contextLoads() {
	//}

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate rest;

	String baseUrl;

	@BeforeEach
	void beforeAll() {
		baseUrl = "http://localhost:" + port + "/books";
	}

	@Test
	void contextLoads() {
		// just verifies Spring started
	}

	@Test
	void addAndGetAndDeleteBook_endToEnd() {
		Book book = new Book("9999", "JUniter", "Spring Testing", 42.0);

		// POST /books
		ResponseEntity<Book> post = rest.postForEntity(baseUrl, book, Book.class);
		assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(post.getBody().getIsbn()).isEqualTo("9999");

		// GET /books/9999
		ResponseEntity<Book> get = rest.getForEntity(baseUrl + "/9999", Book.class);
		assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(get.getBody().getTitle()).isEqualTo("Spring Testing");

		// DELETE /books/9999
		ResponseEntity<Void> del = rest.exchange(
				baseUrl + "/9999", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class
		);
		assertThat(del.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		// GET after delete -> 404
		ResponseEntity<Book> get404 = rest.getForEntity(baseUrl + "/9999", Book.class);
		assertThat(get404.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void updateAndListBooks() {
		Book b1 = new Book("1111", "A1", "T1", 9.99);
		Book b2 = new Book("2222", "A2", "T2", 19.99);

		// ensure clean slate
		rest.delete(baseUrl + "/1111");
		rest.delete(baseUrl + "/2222");

		// add both
		rest.postForEntity(baseUrl, b1, Book.class);
		rest.postForEntity(baseUrl, b2, Book.class);

		// update b1 price
		b1.setPrice(8.88);
		HttpEntity<Book> putReq = new HttpEntity<>(b1);
		ResponseEntity<Book> put = rest.exchange(
				baseUrl, HttpMethod.PUT, putReq, Book.class
		);
		assertThat(put.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(put.getBody().getPrice()).isEqualTo(8.88);

		// GET all
		ResponseEntity<Book[]> all = rest.getForEntity(baseUrl, Book[].class);
		assertThat(all.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<Book> list = List.of(all.getBody());
		assertThat(list).extracting("isbn").containsExactlyInAnyOrder("1111", "2222");
	}

}
