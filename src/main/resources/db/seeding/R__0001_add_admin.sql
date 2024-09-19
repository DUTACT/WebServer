DO
    $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM admin) THEN
            INSERT INTO usercredential (id, username, password)
            VALUES  (1, 'admin', '$2a$10$O.viRD2Y1ntHDNxuuU/ehufBmmeB1s7p.1mnRhSu0xLHVpOYSTwFO');
            INSERT INTO admin (id)
            VALUES (1);
            END IF;
    END;
$$;