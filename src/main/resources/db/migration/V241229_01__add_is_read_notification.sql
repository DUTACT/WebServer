BEGIN;

ALTER TABLE notification.notification
    ADD COLUMN is_read BOOLEAN NOT NULL DEFAULT TRUE;

COMMIT;