BEGIN;

CREATE TABLE event_participation_code
(
    id       UUID PRIMARY KEY,
    event_id INTEGER   NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at   TIMESTAMP NOT NULL
);

ALTER TABLE event_participation_code
    ADD CONSTRAINT fk_event_participation_code__event_id
        FOREIGN KEY (event_id) REFERENCES event (id);

COMMIT;