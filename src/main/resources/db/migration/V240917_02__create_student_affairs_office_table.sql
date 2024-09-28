CREATE TABLE "student_affairs_office" (
    id INTEGER PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES account(id) ON DELETE CASCADE
);