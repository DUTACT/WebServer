CREATE TABLE organization (
      id INTEGER PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      avatar VARCHAR(255),
      enabled BOOLEAN DEFAULT TRUE,
      created_date TIMESTAMP NOT NULL,
      FOREIGN KEY (id) REFERENCES usercredential(id) ON DELETE CASCADE
);