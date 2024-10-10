BEGIN;

CREATE TABLE event_registration
(
    id            SERIAL PRIMARY KEY,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id    INTEGER,
    event_id      INTEGER
);

ALTER TABLE "event_registration"
    ADD CONSTRAINT fk_event_registration__student_id
        FOREIGN KEY (student_id) REFERENCES "student" (id);

ALTER TABLE "event_registration"
    ADD CONSTRAINT fk_event_registration__event_id
        FOREIGN KEY (event_id) REFERENCES "event" (id);

COMMIT;