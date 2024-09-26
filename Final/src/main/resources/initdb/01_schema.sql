CREATE TABLE persona (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         age INTEGER NOT NULL,
                         gender VARCHAR(10),
                         date_of_birth DATE,
                         blood_type VARCHAR(3)
);