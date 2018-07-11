package hnwj.web;

import hnwj.pojo.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
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

        prepareModel(model);
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

        prepareModel(model);
        return "kafka";
    }

    private String getPayload(String topic) {
        List<String> payloads = Arrays.asList("TILSTANDSSKIFT", "PARTSOPDATERING", "JOURNALPOST", "FRIST");
        int i = new Random().nextInt(payloads.size() - 1);
        String randomPayload = payloads.get(i);

//        return "Besked fra KafkaController " + LocalDateTime.now().toString();
        return randomPayload + LocalDateTime.now().toString();
    }


    @RequestMapping("/kafka/consume/pause")
    public String pause(String topic, Model model) {
        LOG.info("pausing consumer...", topic);

        MessageListenerContainer listenerContainer = registry.getListenerContainer("manualStart");
        logListenerContainer(listenerContainer);
        listenerContainer.pause();
        logListenerContainer(listenerContainer);

        prepareModel(model);
        return "kafka";
    }

    @RequestMapping("/kafka/consume/resume")
    public String consume(String topic, Model model) {
        LOG.info("resuming consumer...", topic);

        MessageListenerContainer listenerContainer = registry.getListenerContainer("manualStart");
        logListenerContainer(listenerContainer);
        listenerContainer.resume();
        logListenerContainer(listenerContainer);

        prepareModel(model);
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


    private void prepareModel(Model model) {
        addTopicsToModel(model);
        addListenerContainersToModel(model);
    }


    private void addTopicsToModel(Model model) {
        model.addAttribute("kafka_topics", Arrays.asList(TOPIC, TOPIC2));
    }

    private void addListenerContainersToModel(Model model) {
        Map map = new HashMap<String, MessageListenerContainer>();
        registry.getListenerContainerIds().forEach(id -> {
            MessageListenerContainer listenerContainer = registry.getListenerContainer(id);
            map.put(id, listenerContainer);

            LOG.info("added key='{}' with listener container=''{}", id, listenerContainer);

        });
        model.addAttribute("containers", map);
    }

    private void logListenerContainer(MessageListenerContainer listenerContainer) {
        LOG.info("listenerContainer.isContainerPaused()='{}'", listenerContainer.isContainerPaused());
        LOG.info("listenerContainer.isRunning()='{}'", listenerContainer.isRunning());
    }


}
