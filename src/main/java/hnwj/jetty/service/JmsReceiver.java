package hnwj.jetty.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class JmsReceiver {

    @JmsListener(destination = "mailbox-destination")
    public void receiveMessage(String message) {
        System.out.println(String.format("Received <%s>", message));

    }
}
