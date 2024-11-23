CREATE TABLE feedback_like (
    id SERIAL PRIMARY KEY,
    feedback_id INTEGER NOT NULL REFERENCES feedback(id) ON DELETE CASCADE,
    student_id INTEGER NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    liked_at TIMESTAMP NOT NULL,
    UNIQUE (feedback_id, student_id)
);

CREATE INDEX idx_feedback_like_feedback_id ON feedback_like(feedback_id);
CREATE INDEX idx_feedback_like_student_id ON feedback_like(student_id);