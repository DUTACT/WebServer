BEGIN;

ALTER TABLE event_registration
    ADD COLUMN certificate_status JSONB;

DROP TABLE participation_certificate;

COMMIT;