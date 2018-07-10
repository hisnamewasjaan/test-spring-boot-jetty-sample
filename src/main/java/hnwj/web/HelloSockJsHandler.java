package hnwj.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Simple SockJS handler
 */
public class HelloSockJsHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(HelloSockJsHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug(String.format("Opened new SockJS session in instance %s", this));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(String.format("Received SockJs message:, %s", message));
        session.sendMessage(new TextMessage(String.format("Hello, %s", message.getPayload())));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Transpoort error.", exception);
        session.close(CloseStatus.SERVER_ERROR);
    }
}
