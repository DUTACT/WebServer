CREATE TABLE student (
     id INTEGER PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     phone VARCHAR(255),
     major VARCHAR(255),
     avatar VARCHAR(255),
     enabled BOOLEAN DEFAULT TRUE,
     created_date TIMESTAMP NOT NULL,
     FOREIGN KEY (id) REFERENCES usercredential(id) ON DELETE CASCADE
);
