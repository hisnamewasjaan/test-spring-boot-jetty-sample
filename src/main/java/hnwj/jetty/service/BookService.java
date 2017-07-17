package hnwj.jetty.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 */
@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);


    private final RestTemplate restTemplate;

    public BookService(RestTemplate rest) {
        restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String readingList() {
        String urlStr = "http://localhost8080/recommended";
        LOG.info("calling hystrix protected readingList, attempting to call '{}'", urlStr);
        URI uri = URI.create(urlStr);
        return restTemplate.getForObject(uri, String.class);
    }

    public String reliable() {
        LOG.info("calling hystric fallback method 'reliable'");
        return "[** hystrix fallback **]: Cloud Native Java (O'Reilly)";
    }

}
