package com.dutact.web.features.notification.push;

import com.dutact.web.features.notification.push.exceptions.NotConnectedException;

public interface MessageSender {
    void sendMessage(String subscriptionToken, String message) throws NotConnectedException;
}
