CREATE TABLE post_like (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL REFERENCES post(id) ON DELETE CASCADE,
    student_id INTEGER NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    liked_at TIMESTAMP NOT NULL,
    UNIQUE (post_id, student_id)
);

CREATE INDEX idx_post_like_post_id ON post_like(post_id);
CREATE INDEX idx_post_like_student_id ON post_like(student_id); 