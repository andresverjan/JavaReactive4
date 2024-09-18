CREATE TABLE api.productos (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price INTEGER NOT NULL,
    description VARCHAR(100),
    image_url VARCHAR(100),
	stock INTEGER NOT NULL
);