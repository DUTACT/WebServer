BEGIN;

-- Event table migration
ALTER TABLE "event"
    ADD COLUMN "cover_photos" jsonb;

UPDATE "event"
SET "cover_photos" = CASE
                         WHEN "cover_photo" IS NULL THEN '[]'::jsonb
                         ELSE jsonb_build_array("cover_photo")
    END;

ALTER TABLE "event"
    ALTER COLUMN "cover_photos" SET NOT NULL,
    ALTER COLUMN "cover_photos" SET DEFAULT '[]'::jsonb;

ALTER TABLE "event"
    DROP COLUMN "cover_photo";

-- Post table migration
ALTER TABLE "post"
    ADD COLUMN "cover_photos" jsonb;

UPDATE "post"
SET "cover_photos" = CASE
                         WHEN "cover_photo" IS NULL THEN '[]'::jsonb
                         ELSE jsonb_build_array("cover_photo")
    END;

ALTER TABLE "post"
    ALTER COLUMN "cover_photos" SET NOT NULL,
    ALTER COLUMN "cover_photos" SET DEFAULT '[]'::jsonb;

ALTER TABLE "post"
    DROP COLUMN "cover_photo";

-- Feedback table migration
ALTER TABLE "feedback"
    ADD COLUMN "cover_photos" jsonb;

UPDATE "feedback"
SET "cover_photos" = CASE
                         WHEN "cover_photo" IS NULL THEN '[]'::jsonb
                         ELSE jsonb_build_array("cover_photo")
    END;

ALTER TABLE "feedback"
    ALTER COLUMN "cover_photos" SET NOT NULL,
    ALTER COLUMN "cover_photos" SET DEFAULT '[]'::jsonb;

ALTER TABLE "feedback"
    DROP COLUMN "cover_photo";

COMMIT;