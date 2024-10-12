BEGIN;

ALTER TABLE "event"
    DROP CONSTRAINT fk_event__event_organizer_id;
ALTER TABLE "event"
    ADD CONSTRAINT fk_event__event_organizer_id
        FOREIGN KEY (organizer_id)
            REFERENCES "event_organizer" (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE "post"
    DROP CONSTRAINT fk_post__post_event_id;
ALTER TABLE "post"
    ADD CONSTRAINT fk_post__post_event_id
        FOREIGN KEY (event_id)
            REFERENCES "event" (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE "event_registration"
    DROP CONSTRAINT fk_event_registration__student_id;
ALTER TABLE "event_registration"
    ADD CONSTRAINT fk_event_registration__student_id
        FOREIGN KEY (student_id)
            REFERENCES "student" (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE "event_registration"
    DROP CONSTRAINT fk_event_registration__event_id;
ALTER TABLE "event_registration"
    ADD CONSTRAINT fk_event_registration__event_id
        FOREIGN KEY (event_id)
            REFERENCES "event" (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;
COMMIT;