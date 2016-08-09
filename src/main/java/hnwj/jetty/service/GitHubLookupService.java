package hnwj.jetty.service;

import hnwj.jetty.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 */
@Service
public class GitHubLookupService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<User> findUser(String user) throws InterruptedException {
        log.info(String.format("Looking up %s", user));
        User results = restTemplate.getForObject("https://api.github.com/users/" + user, User.class);

        Thread.sleep(1000L);
        return new AsyncResult<>(results);
    }
}
