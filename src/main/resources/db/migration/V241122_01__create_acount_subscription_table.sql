BEGIN;

CREATE SCHEMA IF NOT EXISTS notification;

CREATE TABLE notification.account_subscription
(
    subscription_token VARCHAR PRIMARY KEY,
    account_id         INTEGER NOT NULL,
    device_id          VARCHAR
);

CREATE UNIQUE INDEX account_subscription_account_id_device_id_idx
    ON notification.account_subscription (account_id, device_id);

COMMIT;