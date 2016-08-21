package hnwj.jetty.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 */
@EnableJms
@RestController
public class JmsSendMessageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    ConfigurableApplicationContext context;


    @GetMapping("/jms/send")
    @ResponseBody
    public String sendMessage(@RequestParam(value = "message", defaultValue = "The Message") final String message) {
        log.info("jms/send - info");

        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        };

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        log.info("Sending a new jms message.");
        jmsTemplate.send("mailbox-destination", messageCreator);

        return String.format("Message [%s] sent..", message);
    }

}
