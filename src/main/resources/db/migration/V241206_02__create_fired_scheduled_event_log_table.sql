BEGIN;

CREATE TABLE notification.fired_scheduled_event_log
(
    id      SERIAL PRIMARY KEY,
    hash    VARCHAR NOT NULL,
    details TEXT
);

CREATE UNIQUE INDEX fired_scheduled_event_log_hash_idx
    ON notification.fired_scheduled_event_log (hash);

COMMIT;