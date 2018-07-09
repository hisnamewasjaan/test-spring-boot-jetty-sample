package hnwj.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class BeskedfordelerEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(BeskedfordelerEventConsumer.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }


//    @KafkaListener(topics = "${kafka.topic.sag-events}")
    @KafkaListener(topics = "beskedfordeler_events", clientIdPrefix = "beskedfordelereventsmodtager")
    public void receive(String payload) {
        LOG.info("received payload='{}' on topic='{beskedfordeler_events}'", payload);
        countDownLatch.countDown();
        LOG.info("latch count='{}'", countDownLatch.getCount());
    }

}
