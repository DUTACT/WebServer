BEGIN;

ALTER TABLE "event"
    DROP COLUMN "cover_photo_url";
ALTER TABLE "event"
    ADD COLUMN "cover_photo" jsonb NOT NULL DEFAULT '{
      "fileId": "",
      "fileUrl": ""
    }';

COMMIT;