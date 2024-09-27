CREATE TABLE api.ventas (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE api.venta_producto (
    orden_id BIGINT REFERENCES api.ventas(id) ON DELETE CASCADE,
    producto_id BIGINT REFERENCES api.productos(id) ON DELETE CASCADE,
    cantidad INT NOT NULL,
    PRIMARY KEY (orden_id, producto_id)
);