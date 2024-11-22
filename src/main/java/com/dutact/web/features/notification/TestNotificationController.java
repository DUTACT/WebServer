package com.dutact.web.features.notification;

import com.dutact.web.features.notification.messaging.PushNotificationWorker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test-notification")
public class TestNotificationController {
    private final PushNotificationWorker pushNotificationWorker;

    public TestNotificationController(PushNotificationWorker pushNotificationWorker) {
        this.pushNotificationWorker = pushNotificationWorker;
    }

    @GetMapping("/send")
    public String sendNotification(@RequestParam("userId") Integer userId, @RequestParam("message") String message) {
        pushNotificationWorker.sendNotification(List.of(userId), message);
        return "Notification sent";
    }
}
