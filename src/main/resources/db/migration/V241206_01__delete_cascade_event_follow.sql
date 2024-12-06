BEGIN;

ALTER TABLE event_follow
    DROP CONSTRAINT fk_event_follow__event_id;

ALTER TABLE event_follow
    ADD CONSTRAINT fk_event_follow__event_id FOREIGN KEY (event_id) REFERENCES event (id) ON DELETE CASCADE;

COMMIT;