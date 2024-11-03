BEGIN;

ALTER TABLE "event_organizer"
    ADD COLUMN person_in_charge_name VARCHAR(255),
    ADD COLUMN phone VARCHAR(255),
    ADD COLUMN address VARCHAR(255);
COMMIT;