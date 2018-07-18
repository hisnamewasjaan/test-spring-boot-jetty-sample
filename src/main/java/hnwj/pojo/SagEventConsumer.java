package hnwj.pojo;

import hnwj.eventproducer.SagEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class SagEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SagEventConsumer.class);

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private CountDownLatch countDownLatch1 = new CountDownLatch(10);


    @KafkaListener(
            id = "manualStart",
            topics = "sag_events",
            groupId = "Gruppe1",
            containerFactory = "jsonKafkaListenerContainerFactory",
            clientIdPrefix = "sageventsjsonmodtager")
    public void receive1(SagEvent payload) {
        LOG.info("Gruppe1: received payload='{}' on topic='{sag_events}'", payload);
        if (payload.getBidragsyder() != null) {
            LOG.info("Gruppe1: Sag '{}' opdateret af '{}'. Tilstand er nu '{}'", payload.getSagsnummer(), payload.getBidragsyder(), payload.getTilstandsskift());

        } else {
            LOG.info("Gruppe1: Sag '{}' har udløbet frist '{}'", payload.getSagsnummer(), payload.getUdløbetFrist());

        }
        countDownLatch1.countDown();
        LOG.info("Gruppe1: latch count='{}'", countDownLatch1.getCount());

        relayToStompTopic(payload);
    }


    private void relayToStompTopic(SagEvent message) {
        LOG.info("Relaying message={}", message);
        simpMessagingTemplate.convertAndSend("/topic/sag_event", message);
    }

}
