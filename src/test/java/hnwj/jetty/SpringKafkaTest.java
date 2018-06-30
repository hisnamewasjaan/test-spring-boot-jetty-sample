package hnwj.jetty;

import hnwj.pojo.KafkaReceiver;
import hnwj.pojo.KafkaSender;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaTest {

    static final String TOPIC = "hellohnwj.t";

//    @ClassRule
//    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TOPIC);

    @Autowired
    KafkaReceiver receiver;

    @Autowired
    KafkaSender sender;

    @Test
    public void testReceive() throws Exception {
        sender.send(TOPIC, "Hello jan");

        receiver.getCountDownLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(receiver.getCountDownLatch().getCount(), 0);
        assertThat(receiver.getCountDownLatch().getCount()).isEqualTo(0);
    }
}
