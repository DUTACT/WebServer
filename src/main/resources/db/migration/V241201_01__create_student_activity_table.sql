BEGIN;

CREATE TABLE "student_activity"
(
    id          SERIAL PRIMARY KEY,
    student_id  INTEGER                     NOT NULL,
    type        VARCHAR(50)                 NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_id    INTEGER,
    post_id     INTEGER,
    feedback_id INTEGER
);

-- Add foreign key constraints with cascade delete
ALTER TABLE "student_activity"
    ADD CONSTRAINT fk_student_activity__student_id
        FOREIGN KEY (student_id) REFERENCES "student" (id)
            ON DELETE CASCADE;

ALTER TABLE "student_activity"
    ADD CONSTRAINT fk_student_activity__event_id
        FOREIGN KEY (event_id) REFERENCES "event" (id)
            ON DELETE CASCADE;

-- Create indexes for better query performance
CREATE INDEX idx_student_activity__student_id ON "student_activity" (student_id);
CREATE INDEX idx_student_activity__created_at ON "student_activity" (created_at);
CREATE INDEX idx_student_activity__type ON "student_activity" (type);

COMMIT;