package hnwj.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class KafkaReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiver.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }


    @KafkaListener(topics = "${kafka.topic.hellohnwj}")
    public void receive(String payload) {
        LOG.info("received payload='{}'", payload);
        countDownLatch.countDown();
    }

}
