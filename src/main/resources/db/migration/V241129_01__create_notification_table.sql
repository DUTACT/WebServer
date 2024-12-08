BEGIN;

DROP TABLE IF EXISTS notification.send_failed_notification;

CREATE TABLE notification.notification
(
    id                SERIAL PRIMARY KEY,
    details           TEXT,
    notification_type VARCHAR(255),
    account_id        INTEGER NOT NULL,
    created_at        TIMESTAMP,
    CONSTRAINT fk_notification_account_id FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

COMMIT;