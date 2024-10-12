BEGIN;

ALTER TABLE "post"
    DROP COLUMN "cover_photo_url";
ALTER TABLE "post"
    ADD COLUMN "cover_photo" jsonb NOT NULL DEFAULT '{
      "fileId": "",
      "fileUrl": ""
    }';

COMMIT;