package hnwj.jetty.web;

import hnwj.pojo.KafkaSender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@EnableAutoConfiguration
@Controller
public class KafkaController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaController.class);

    private static final String TOPIC = "hellohnwj.t";
    private static final String TOPIC2 = "hnwj.t";

    @Autowired
    KafkaSender sender;

//    @Autowired
//    ConcurrentKafkaListenerContainerFactory jsonKafkaListenerContainerFactory;

    @Autowired
    public KafkaListenerEndpointRegistry registry;

    @RequestMapping("/kafka")
    public String kafka(Model model) {
        LOG.info("kafka");

        registry.getListenerContainers().forEach(messageListenerContainer -> LOG.info(String.valueOf(messageListenerContainer.getContainerProperties())));
        registry.getListenerContainerIds().forEach(id -> LOG.info(String.valueOf(id)));

        MessageListenerContainer listenerContainer = registry.getListenerContainer("manualStart");
        LOG.info("listenerContainer.isContainerPaused()='{}'", listenerContainer.isContainerPaused());
        LOG.info("listenerContainer.isRunning()='{}'", listenerContainer.isRunning());
        listenerContainer.resume();
        listenerContainer.start();
        LOG.info("listenerContainer.isContainerPaused()='{}'", listenerContainer.isContainerPaused());
        LOG.info("listenerContainer.isRunning()='{}'", listenerContainer.isRunning());

        addTopicsToModel(model);

        return "kafka";
    }

    @RequestMapping("/kafka/produce")
    public String produce(String topic, Model model) {
//        LOG.info("Publishing kafka message on topic: '{}'...", TOPIC);
        LOG.info("Publishing kafka message on topic: '{}'...", topic);

        if (!StringUtils.isEmpty(topic)) {

            String payload = getPayload(topic);
            sender.send(topic, payload);
            model.addAttribute("payload", payload);
            model.addAttribute("topic", topic);
        } else {
            LOG.info("Intet topic - ingen besked..");
        }

        addTopicsToModel(model);

        return "kafka";
    }

    private String getPayload(String topic) {
        List<String> payloads = Arrays.asList("TILSTANDSSKIFT", "PARTSOPDATERING", "JOURNALPOST", "FRIST");
        int i = new Random().nextInt(payloads.size() - 1);
        String randomPayload = payloads.get(i);

//        return "Besked fra KafkaController " + LocalDateTime.now().toString();
        return randomPayload + LocalDateTime.now().toString();
    }


    @RequestMapping("/kafka/consume")
    public String consume(String topic, Model model) {
        LOG.info("Consuming kafka messages on topic: '{}'...", topic);



        if (!StringUtils.isEmpty(topic)) {

//            model.addAttribute("payload", payload);
//            model.addAttribute("topic", topic);
        } else {
            LOG.info("Intet topic - ingen forbrug..");
        }

        addTopicsToModel(model);
        return "kafka";
    }


//    private void start(String topic) {
//        ContainerProperties containerProps = new ContainerProperties("topic1", "topic2");
//        KafkaMessageListenerContainer<Integer, String> container = createContainer(containerProps);
//        containerProps.setMessageListener(new MessageListener<Integer, String>() {
//
//            @Override
//            public void onMessage(ConsumerRecord<Integer, String> message) {
//                LOG.info("received: " + message);
//            }
//
//        });
//        container.setBeanName("testAuto");
//        container.start();
//    }


    private void addTopicsToModel(Model model) {
        model.addAttribute("kafka_topics", Arrays.asList(TOPIC, TOPIC2));
    }


}
