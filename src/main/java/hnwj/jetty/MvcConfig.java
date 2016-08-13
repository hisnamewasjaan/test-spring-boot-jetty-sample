package hnwj.jetty;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("hat");
        registry.addViewController("/hat").setViewName("hat");
        registry.addViewController("/helloh").setViewName("helloh");
        registry.addViewController("/login").setViewName("login");
    }
}
