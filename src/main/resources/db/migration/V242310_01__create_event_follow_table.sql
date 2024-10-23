BEGIN;

CREATE TABLE event_follow
(
    id            SERIAL PRIMARY KEY,
    follow_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id    INTEGER,
    event_id      INTEGER
);

ALTER TABLE "event_follow"
    ADD CONSTRAINT fk_event_follow__student_id
        FOREIGN KEY (student_id) REFERENCES "student" (id);

ALTER TABLE "event_follow"
    ADD CONSTRAINT fk_event_follow__event_id
        FOREIGN KEY (event_id) REFERENCES "event" (id);

COMMIT;