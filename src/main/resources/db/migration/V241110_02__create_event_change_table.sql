BEGIN;

CREATE TABLE event_change
(
    id         SERIAL PRIMARY KEY,
    details    JSONB                       NOT NULL,
    changed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

COMMIT;