CREATE TABLE student (
     id INTEGER PRIMARY KEY,
     full_name VARCHAR(255) NOT NULL,
     phone VARCHAR(255),
     faculty VARCHAR(255),
     avatar_url VARCHAR(255),
     FOREIGN KEY (id) REFERENCES account(id) ON DELETE CASCADE
);
