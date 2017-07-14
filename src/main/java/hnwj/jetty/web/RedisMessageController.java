package hnwj.jetty.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * redis must be started:
 * <p>
 * redis-server --protected-mode no --loglevel verbose
 */
@Controller
@Profile("redis")
public class RedisMessageController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisMessageController.class);

    @Value("${redis.message.topic}")
    private String topic;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    CountDownLatch latch;


    @RequestMapping("/redis")
    public String redis(Model model) {
        LOG.info("redis");
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        Set<String> keys = stringRedisTemplate.keys("*");

        model.addAttribute("redis_keys", keys);

        return "redis";
    }

    @RequestMapping("/redis/key")
    public String redisKey(String key, Model model) {
        LOG.info("redis");
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();

        model.addAttribute("redis_keys", stringRedisTemplate.keys("*"));

        DataType dataTypeForKey = stringRedisTemplate.type(key);
        LOG.info("Requested redis key '{}' with data type '{}'", key, dataTypeForKey);

        Object value;
        switch (dataTypeForKey) {
            case LIST:
                LOG.info("Getting LIST value for redis key '{}'", key);
                RedisOperations<String, String> operations = valueOps.getOperations();
                BoundListOperations<String, String> stringStringBoundListOperations = operations.boundListOps(key);
                List<String> range = stringStringBoundListOperations.range(-4, -1);
                value = range;
                break;
            case STRING:
                LOG.info("Getting STRING value for redis key '{}'", key);
                value = valueOps.get(key);
                break;
            case NONE:
                LOG.info("Getting NONE value for redis key '{}'", key);
                value = valueOps.get(key);
                break;
            default:
                LOG.warn("Unsupported key type '{}', requested, for key '{}'", dataTypeForKey, key);
                value = null;
        }


        model.addAttribute("redis_value", value);

        return "redis";
    }

    @RequestMapping("/redis/message")
    public String redisMessage() {

        LOG.info("Publishing Redis message on topic: '{}'...", topic);
        stringRedisTemplate.dump("hnwj-app");
        stringRedisTemplate.convertAndSend(topic, "Hello from Redis!");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redis";
    }
}
