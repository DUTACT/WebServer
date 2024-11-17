BEGIN;

CREATE TABLE event_change
(
    id         SERIAL PRIMARY KEY,
    details    JSONB                       NOT NULL,
    changed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id   INTEGER                     NOT NULL
);

ALTER TABLE "event_change"
    ADD CONSTRAINT "event_change_event_id_fkey"
        FOREIGN KEY ("event_id")
            REFERENCES "event" ("id")
            ON DELETE CASCADE;

COMMIT;