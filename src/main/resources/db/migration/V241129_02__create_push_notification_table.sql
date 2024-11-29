BEGIN;

CREATE TABLE notification.push_notification
(
    id                 SERIAL PRIMARY KEY,
    message            TEXT NOT NULL,
    subscription_token VARCHAR NOT NULL,
    CONSTRAINT fk_push_notification_subscription_token
        FOREIGN KEY (subscription_token)
            REFERENCES notification.account_subscription (subscription_token) ON DELETE CASCADE
);

COMMIT;