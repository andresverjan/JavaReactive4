CREATE TABLE api.ordenes_compras (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE api.orden_compra_producto (
    orden_id BIGINT REFERENCES api.ordenes_compras(id) ON DELETE CASCADE,
    producto_id BIGINT REFERENCES api.productos(id) ON DELETE CASCADE,
    cantidad INT NOT NULL,
    PRIMARY KEY (orden_id, producto_id)
);
