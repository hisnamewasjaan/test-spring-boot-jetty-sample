package hnwj.jetty;

import hnwj.jetty.web.HelloSockJsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Simple websocket (SockJS) configuration
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(helloSockJsHandler(), "/hello_sockjs").withSockJS();
        log.info(String.format("Registered SockJs handler: %s", "/hello_sockjs"));
    }

    @Bean
    public WebSocketHandler helloSockJsHandler() {
        return new HelloSockJsHandler();
    }


}
