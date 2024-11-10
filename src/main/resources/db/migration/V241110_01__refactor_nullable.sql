BEGIN;
-- Event registration
ALTER TABLE "event_registration"
    ALTER COLUMN "event_id" SET NOT NULL;

ALTER TABLE "event_registration"
    ALTER COLUMN "student_id" SET NOT NULL;

ALTER TABLE "event_registration"
    DROP CONSTRAINT "fk_event_registration__event_id";
ALTER TABLE "event_registration"
    ADD CONSTRAINT "fk_event_registration__event_id" FOREIGN KEY ("event_id") REFERENCES "event" ("id") ON DELETE CASCADE;

ALTER TABLE "event_registration"
    DROP CONSTRAINT "fk_event_registration__student_id";
ALTER TABLE "event_registration"
    ADD CONSTRAINT "fk_event_registration__student_id" FOREIGN KEY ("student_id") REFERENCES "student" ("id") ON DELETE CASCADE;


-- Feedback
ALTER TABLE "feedback"
    ALTER COLUMN "content" SET NOT NULL;

ALTER TABLE "feedback"
    ALTER COLUMN "posted_at" SET NOT NULL;

-- Participation certificate
ALTER TABLE "participation_certificate"
    DROP CONSTRAINT "fk_participation_certificate__event_id";
ALTER TABLE "participation_certificate"
    ADD CONSTRAINT "fk_participation_certificate__event_id" FOREIGN KEY ("event_id") REFERENCES "event" ("id") ON DELETE CASCADE;

ALTER TABLE "participation_certificate"
    DROP CONSTRAINT "fk_participation_certificate__student_id";
ALTER TABLE "participation_certificate"
    ADD CONSTRAINT "fk_participation_certificate__student_id" FOREIGN KEY ("student_id") REFERENCES "student" ("id") ON DELETE CASCADE;

-- Event check in code
ALTER TABLE "event_checkin_code"
    DROP CONSTRAINT "fk_event_participation_code__event_id";
ALTER TABLE "event_checkin_code"
    ADD CONSTRAINT "fk_event_checkin_code__event_id" FOREIGN KEY ("event_id") REFERENCES "event" ("id") ON DELETE CASCADE;
COMMIT;