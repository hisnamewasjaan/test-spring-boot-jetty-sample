package hnwj.config;

import hnwj.eventproducer.SagEvent;
import hnwj.pojo.BeskedfordelerEventConsumer;
import hnwj.pojo.SagEventConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Configuration
@EnableKafka
//@Profile("kafka")
public class KafkaJsonReceiverConfig {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaJsonReceiverConfig.class);


    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    public Map<String, Object> jsonConsumerConfigs() {
        LOG.info("consumerConfigs");
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Gruppe1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "hnwj.eventproducer");
        return props;
    }


    @Bean
    public ConsumerFactory<String, SagEvent> jsonConsumerFactory() {
        LOG.info("consumerFactory");


        return new DefaultKafkaConsumerFactory<>(
                jsonConsumerConfigs());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, SagEvent> jsonKafkaListenerContainerFactory() {
        LOG.info("kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, SagEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonConsumerFactory());
//        factory.setAutoStartup(Boolean.FALSE);
        return factory;
    }


    @Bean
    public SagEventConsumer sagEventConsumer() {
        return new SagEventConsumer();
    }

    @Bean
    public BeskedfordelerEventConsumer beskedfordelerEventConsumer() {
        return new BeskedfordelerEventConsumer();
    }


}
