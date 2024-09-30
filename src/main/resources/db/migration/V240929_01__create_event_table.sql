BEGIN;

CREATE TABLE "event"
(
    id                    SERIAL PRIMARY KEY,
    name                  VARCHAR(255)                NOT NULL,
    content               TEXT                        NOT NULL,
    start_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_at                TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    start_registration_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_registration_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cover_photo_url       VARCHAR(1023)               NOT NULL,
    status                jsonb                       NOT NULL,
    organizer_id          INTEGER                     NOT NULL
);

CREATE INDEX idx_event_status_type ON "event" ((status ->> 'type'));
ALTER TABLE "event"
    ADD CONSTRAINT fk_event__event_organizer_id FOREIGN KEY (organizer_id) REFERENCES "event_organizer" (id);

COMMIT;