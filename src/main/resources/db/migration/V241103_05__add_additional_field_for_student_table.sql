BEGIN;

ALTER TABLE "event_organizer"
    DROP COLUMN "avatar_url";
ALTER TABLE "event_organizer"
    ADD COLUMN "avatar" JSONB;

COMMIT;