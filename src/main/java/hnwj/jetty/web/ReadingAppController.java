package hnwj.jetty.web;

import hnwj.jetty.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@EnableCircuitBreaker
public class ReadingAppController {

    @Autowired
    private BookService bookService;


    @RequestMapping("/to-read")
    public String readingList() {
        return bookService.readingList();
    }
}
