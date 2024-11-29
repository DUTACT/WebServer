package com.dutact.web.features.notification.infrastructure.websocket;

import com.dutact.web.auth.factors.AccountService;
import com.dutact.web.features.notification.infrastructure.connection.ConnectionHandler;
import com.dutact.web.features.notification.subscription.AccountSubscriptionHandler;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.dutact.web.features.notification.infrastructure.websocket.SSPRMessageCommand.*;

@Log4j2
@Component
public class SSPRNotificationWebSocketHandler extends TextWebSocketHandler {
    private static final String HEADER_DEVICE_ID = "device-id";
    private static final String HEADER_ACCESS_TOKEN = "access-token";
    private static final String HEADER_SUBSCRIPTION_TOKEN = "subscription-token";
    private static final String HEADER_RECEIPT = "receipt";

    private final SSPRMessageMapper SSPRMessageMapper;
    private final AccountSubscriptionHandler accountSubscriptionHandler;
    private final ConnectionHandler connectionHandler;
    private final AccountService accountService;

    public SSPRNotificationWebSocketHandler(SSPRMessageMapper SSPRMessageMapper,
                                            AccountSubscriptionHandler accountSubscriptionHandler,
                                            ConnectionHandler connectionHandler,
                                            AccountService accountService) {
        super();
        this.SSPRMessageMapper = SSPRMessageMapper;
        this.accountSubscriptionHandler = accountSubscriptionHandler;
        this.connectionHandler = connectionHandler;
        this.accountService = accountService;
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        var ssprSession = new SSPRWebsocketSession(session, SSPRMessageMapper);
        var ssprMessage = new SSPRMessage();

        try {
            ssprMessage = SSPRMessageMapper.toSSPRMessage(payload);
        } catch (Exception e) {
            var response = new SSPRMessage();
            response.setCommand(ERROR);
            response.setBody("Invalid message format");

            ssprSession.send(response);
            return;
        }

        var receipt = ssprMessage.getHeaders().get(HEADER_RECEIPT);

        try {
            var response = switch (ssprMessage.getCommand()) {
                case SUBSCRIBE -> handleSubscribe(ssprMessage);
                case UNSUBSCRIBE -> handleUnsubscribe(ssprMessage);
                case CONNECT -> handleConnect(ssprSession, ssprMessage);
                case DISCONNECT -> handleDisconnect(ssprMessage);
                default -> unsupportedCommand();
            };

            response.getHeaders().put(HEADER_RECEIPT, receipt);
            ssprSession.send(response);
        } catch (HeaderNotFoundException e) {
            var response = headerRequired(e.getHeader());
            response.getHeaders().put(HEADER_RECEIPT, receipt);
            response.setBody("Header [" + e.getHeader() + "] is required");

            ssprSession.send(response);
        } catch (Exception e) {
            log.error("Error handling message", e);

            var response = new SSPRMessage();
            response.setCommand(ERROR);
            response.getHeaders().put(HEADER_RECEIPT, receipt);
            response.setBody("Something went wrong: " + e.getMessage());

            ssprSession.send(response);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        connectionHandler.disconnect(session.getId());
    }

    private SSPRMessage handleSubscribe(SSPRMessage ssprMessage) throws HeaderNotFoundException {
        var headersAccessor = new SSPRMessageHeaderAccessor(ssprMessage);
        var deviceId = headersAccessor.getRequired(HEADER_DEVICE_ID);
        var accessToken = headersAccessor.getRequired(HEADER_ACCESS_TOKEN);
        var accountId = accountService.getAccountIdByToken(accessToken);
        var subscriptionToken = accountSubscriptionHandler.subscribe(deviceId, accountId);

        var response = new SSPRMessage();
        response.setCommand(OK);
        response.getHeaders().put(HEADER_SUBSCRIPTION_TOKEN, subscriptionToken);

        return response;
    }

    private SSPRMessage handleUnsubscribe(SSPRMessage ssprMessage) throws HeaderNotFoundException {
        var headerAccessor = new SSPRMessageHeaderAccessor(ssprMessage);
        var subscriptionToken = headerAccessor.getRequired(HEADER_SUBSCRIPTION_TOKEN);
        accountSubscriptionHandler.unsubscribe(subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage handleConnect(SSPRSession session, SSPRMessage ssprMessage) throws HeaderNotFoundException {
        var headerAccessor = new SSPRMessageHeaderAccessor(ssprMessage);
        var subscriptionToken = headerAccessor.getRequired(HEADER_SUBSCRIPTION_TOKEN);

        connectionHandler.connect(session, subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage handleDisconnect(SSPRMessage ssprMessage) throws HeaderNotFoundException {
        var headerAccessor = new SSPRMessageHeaderAccessor(ssprMessage);
        var subscriptionToken = headerAccessor.getRequired(HEADER_SUBSCRIPTION_TOKEN);
        connectionHandler.disconnect(subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage unsupportedCommand() {
        var response = new SSPRMessage();
        response.setCommand(ERROR);
        response.setBody("Unsupported command");

        return response;
    }

    private SSPRMessage headerRequired(String header) {
        var response = new SSPRMessage();
        response.setCommand(ERROR);
        response.setBody("Header " + header + " is required");

        return response;
    }

    @Getter
    private static class HeaderNotFoundException extends Exception {
        private final String header;

        public HeaderNotFoundException(String header) {
            super("Header " + header + " not found");
            this.header = header;
        }
    }

    private static class SSPRMessageHeaderAccessor {
        private final SSPRMessage ssprMessage;

        public SSPRMessageHeaderAccessor(SSPRMessage ssprMessage) {
            this.ssprMessage = ssprMessage;
        }

        @Nonnull
        public String getRequired(String header) throws HeaderNotFoundException {
            var value = ssprMessage.getHeaders().get(header);
            if (value == null) {
                throw new HeaderNotFoundException(header);
            }

            return value;
        }
    }
}
