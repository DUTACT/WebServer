BEGIN;

ALTER TABLE event_participation_code
    ADD COLUMN title TEXT NOT NULL DEFAULT '';

COMMIT;