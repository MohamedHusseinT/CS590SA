package contacts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ContactListener {
    @JmsListener(destination = "testQueue")
    public void receiveMessage(final String contactAsString) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Contact contact = objectMapper.readValue(contactAsString, Contact.class);
            System.out.println("JMS receiver received message:" + contact);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
