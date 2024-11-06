BEGIN;

CREATE TABLE participation_certificate
(
    event_id   INTEGER NOT NULL,
    student_id INTEGER NOT NULL,
    status     JSONB   NOT NULL,
    PRIMARY KEY (event_id, student_id)
);

ALTER TABLE "participation_certificate"
    ADD CONSTRAINT fk_participation_certificate__event_id
        FOREIGN KEY (event_id) REFERENCES "event" (id);

ALTER TABLE "participation_certificate"
    ADD CONSTRAINT fk_participation_certificate__student_id
        FOREIGN KEY (student_id) REFERENCES "student" (id);

CREATE INDEX idx_participation_certificate__student_id
    ON "participation_certificate" (student_id);

COMMIT;