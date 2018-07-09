package hnwj.pojo;

import hnwj.eventproducer.SagEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class SagEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SagEventConsumer.class);

    private CountDownLatch countDownLatch1 = new CountDownLatch(10);
    private CountDownLatch countDownLatch2 = new CountDownLatch(10);

    public CountDownLatch getCountDownLatch() {
        return countDownLatch1;
    }

    private String asd = "**";

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
    }

//    @KafkaListener(topics = "${kafka.topic.sag-events}")
//    @KafkaListener(topics = "sag_events", groupId = "Gruppe2", clientIdPrefix = "sageventsmodtager")
//    public void receive2(String payload) {
//        LOG.info("Gruppe2: received payload='{}' on topic='{sag_events}'", payload);
//        countDownLatch2.countDown();
//        LOG.info("Gruppe2: latch count='{}'", countDownLatch2.getCount());
//    }

}
