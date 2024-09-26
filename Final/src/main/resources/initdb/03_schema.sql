-- Tabla de productos
CREATE TABLE producto (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          description TEXT,
                          image_url VARCHAR(255),
                          stock INTEGER NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla carrito para almacenar los productos agregados por el usuario
CREATE TABLE carrito (
                         id BIGSERIAL PRIMARY KEY,
                         producto_id BIGINT NOT NULL REFERENCES producto(id) ON DELETE CASCADE,  -- Referencia a la tabla de productos
                         user_id BIGINT NOT NULL,  -- Referencia al usuario o sesión
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         quantity INTEGER NOT NULL
);

-- Tabla para las órdenes de compra
CREATE TABLE orden_compra (
                              id BIGSERIAL PRIMARY KEY,
                              fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              estado VARCHAR(20) NOT NULL,
                              total DECIMAL(10, 2),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orden_venta (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             estado VARCHAR(20) NOT NULL,
                             total DECIMAL(10, 2),
                             producto_ids BIGINT[], -- Esta columna almacena una lista de IDs de productos
                             cantidades INTEGER[]
);

-- Tabla para asociar las órdenes con los productos comprados
CREATE TABLE orden_producto (
                                id BIGSERIAL PRIMARY KEY,
                                orden_id BIGINT NOT NULL REFERENCES orden_venta(id) ON DELETE CASCADE,
                                producto_ids BIGINT[],  -- Lista de IDs de productos
                                cantidades INTEGER[],   -- Lista de cantidades de cada producto
                                precios DECIMAL(10, 2)[]  -- Lista de precios de cada producto
);


