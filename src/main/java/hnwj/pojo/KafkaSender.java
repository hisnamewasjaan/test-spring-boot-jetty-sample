package hnwj.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSender {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    public void send(String topic, String payload) {
        LOG.info("Sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

}
