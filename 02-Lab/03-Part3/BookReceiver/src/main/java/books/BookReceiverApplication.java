package books;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class BookReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReceiverApplication.class, args);
    }

}
