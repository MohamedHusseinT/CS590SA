package books;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import books.domain.*;


@Component
public class BookMessageListener {
    @JmsListener(destination = "bookQueue")
    public void receive(Book book) {
        System.out.println("🟢 Received JMS message: " + book);
    }


}
