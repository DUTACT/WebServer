BEGIN;

CREATE TABLE "post"
(
    id                  SERIAL PRIMARY KEY,
    content             TEXT                        NOT NULL,
    posted_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cover_photo_url     VARCHAR(1023)               NOT NULL,
    status              jsonb                       NOT NULL,
    event_id            INTEGER                     NOT NULL
);

CREATE INDEX idx_post_status_type ON "post" ((status ->> 'type'));
ALTER TABLE "post"
    ADD CONSTRAINT fk_post__post_event_id FOREIGN KEY (event_id) REFERENCES "event" (id);

COMMIT;