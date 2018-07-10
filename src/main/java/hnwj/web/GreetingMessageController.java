package hnwj.web;

import hnwj.domain.Greeting;
import hnwj.domain.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Mappings for the STOMP end point.
 */
@Controller
public class GreetingMessageController {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        log.info(String.format("Received a greeting message: %s", message.getName()));

        Thread.sleep(3000L);
        return new Greeting(1L, String.format("Hello, %s!", message.getName()));
    }

    @MessageMapping("/bye")
    @SendTo("/topic/goodbyes")
    public Greeting bye(HelloMessage message) throws InterruptedException {
        log.info(String.format("Received a goodbye message: %s", message.getName()));

        Thread.sleep(3000L);
        return new Greeting(1L, String.format("Bye, %s!", message.getName()));
    }

}
