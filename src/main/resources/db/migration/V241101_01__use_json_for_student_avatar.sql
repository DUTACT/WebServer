BEGIN;

ALTER TABLE "student"
    DROP COLUMN "avatar_url";
ALTER TABLE "student"
    ADD COLUMN "avatar" JSONB;

COMMIT;