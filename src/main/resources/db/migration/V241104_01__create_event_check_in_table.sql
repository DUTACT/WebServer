BEGIN;

CREATE TABLE event_check_in
(
    id               SERIAL PRIMARY KEY,
    check_in_time    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    check_in_code_id UUID                        NOT NULL,
    student_id       INTEGER                     NOT NULL
);

ALTER TABLE "event_check_in"
    ADD CONSTRAINT fk_event_check_in__checkin_code_id
        FOREIGN KEY (check_in_code_id) REFERENCES "event_checkin_code" (id);

ALTER TABLE "event_check_in"
    ADD CONSTRAINT fk_event_check_in__student_id
        FOREIGN KEY (student_id) REFERENCES "student" (id);

COMMIT;