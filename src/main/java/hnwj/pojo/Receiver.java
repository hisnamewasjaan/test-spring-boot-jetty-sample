package hnwj.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 */
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch countDownLatch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        countDownLatch = latch;
    }

    public void receiveMessage(String message) {
        LOG.info("Received<{}>", message);
        countDownLatch.countDown();
    }
}
