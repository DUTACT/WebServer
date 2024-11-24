BEGIN;

CREATE TABLE notification.send_failed_notification
(
    id                 SERIAL PRIMARY KEY,
    subscription_token VARCHAR NOT NULL,
    message            TEXT NOT NULL,
    retries            INTEGER DEFAULT 0
);

COMMIT;