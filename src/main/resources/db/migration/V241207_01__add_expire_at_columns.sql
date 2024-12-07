BEGIN;

-- scheduled_job table
ALTER TABLE notification.scheduled_job
    ADD COLUMN expire_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX idx_scheduled_job_expire_at
    ON notification.scheduled_job (expire_at);

-- push_notification table
ALTER TABLE notification.push_notification
    ADD COLUMN expire_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX idx_push_notification_expire_at
    ON notification.scheduled_job (expire_at);

-- notification table
ALTER TABLE notification.notification
    ADD COLUMN expire_at TIMESTAMP WITH TIME ZONE;

CREATE INDEX idx_notification_expire_at
    ON notification.notification (expire_at);

COMMIT;