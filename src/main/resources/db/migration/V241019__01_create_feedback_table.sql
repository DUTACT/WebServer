BEGIN;

CREATE TABLE "feedback"
(
    student_id  INTEGER NOT NULL,
    event_id    INTEGER NOT NULL,
    content     TEXT,
    posted_at   TIMESTAMP,
    cover_photo JSONB,
    PRIMARY KEY (student_id, event_id)
);

ALTER TABLE "feedback"
    ADD CONSTRAINT fk_feedback__student_id FOREIGN KEY (student_id) REFERENCES "student" (id) ON DELETE CASCADE;

ALTER TABLE "feedback"
    ADD CONSTRAINT fk_feedback__event_id FOREIGN KEY (event_id) REFERENCES "event" (id) ON DELETE CASCADE;

COMMIT;