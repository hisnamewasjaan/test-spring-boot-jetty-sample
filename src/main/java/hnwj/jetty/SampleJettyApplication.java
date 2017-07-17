package hnwj.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "hnwj")
public class SampleJettyApplication {


    private static final Logger log = LoggerFactory.getLogger(SampleJettyApplication.class);

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }


    public static void main(String[] args) throws Exception {
        log.info("**** MAIN **** SpringBootApplication ****");
        SpringApplication.run(SampleJettyApplication.class, args);


    }

}
