BEGIN;

ALTER TABLE event_registration
    ADD COLUMN certificate_status JSONB NOT NULL DEFAULT '{"type": "pending"}';

DROP TABLE participation_certificate;

COMMIT;