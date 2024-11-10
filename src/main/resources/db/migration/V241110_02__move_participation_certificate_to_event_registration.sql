BEGIN;

-- Add status column to event_registration
ALTER TABLE event_registration
    ADD COLUMN certificate_status JSONB;

-- Migrate existing data if needed
INSERT INTO event_registration (event_id, student_id, certificate_status)
SELECT event_id, student_id, status
FROM participation_certificate
ON CONFLICT (event_id, student_id) DO UPDATE
SET certificate_status = EXCLUDED.certificate_status;

-- Drop the participation_certificate table
DROP TABLE participation_certificate;

COMMIT;