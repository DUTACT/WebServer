CREATE TABLE "event_organizer" (
      id INTEGER PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      avatar_url VARCHAR(255),
      FOREIGN KEY (id) REFERENCES account(id) ON DELETE CASCADE
);