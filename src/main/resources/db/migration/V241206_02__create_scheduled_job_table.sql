BEGIN;

CREATE TABLE notification.scheduled_job
(
    id             SERIAL PRIMARY KEY,
    compare_string TEXT                        NOT NULL,
    details        TEXT                        NOT NULL,
    type           VARCHAR(255)                NOT NULL,
    fire_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE INDEX idx_scheduled_job_fire_at
    ON notification.scheduled_job (fire_at);

CREATE INDEX idx_scheduled_job_type_compare_string
    ON notification.scheduled_job (type, compare_string);

COMMIT;