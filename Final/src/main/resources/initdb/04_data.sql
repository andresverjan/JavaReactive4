-- Insertar productos en la tabla de productos
INSERT INTO producto (name, price, description, image_url, stock)
VALUES
    ('MacBook', 1500.00, 'Laptop Apple MacBook', 'macbook.png', 10),
    ('Lenovo ThinkPad', 1200.00, 'Laptop Lenovo ThinkPad', 'lenovo.png', 15),
    ('Dell XPS', 1300.00, 'Laptop Dell XPS', 'dellxps.png', 20),
    ('HP Spectre', 1400.00, 'Laptop HP Spectre', 'hpspectre.png', 8),
    ('Acer Aspire', 900.00, 'Laptop Acer Aspire', 'aceraspire.png', 12);

-- Insertar productos en el carrito
INSERT INTO carrito (producto_id, user_id, name, price, quantity)
VALUES
    (1, 1, 'MacBook', 1500.00, 2),
    (2, 1, 'Lenovo ThinkPad', 1200.00, 1),
    (3, 2, 'Dell XPS', 1300.00, 3),
    (4, 2, 'HP Spectre', 1400.00, 1),
    (5, 3, 'Acer Aspire', 900.00, 2);


-- Crear órdenes de compra (compra para reabastecer inventario)
INSERT INTO orden_compra (fecha_orden, estado, total)
VALUES
    ('2024-09-15 09:00:00', 'Completada', 15000.00),  -- Reabastecimiento de varios productos
    ('2024-09-18 11:00:00', 'Completada', 9000.00);    -- Reabastecimiento adicional


-- Inserta las órdenes en la tabla orden_venta primero
INSERT INTO orden_venta (user_id, estado, total, producto_ids, cantidades)
VALUES
    (1, 'Creada', 3000.00, ARRAY[1, 2], ARRAY[2, 1]),
    (2, 'Creada', 2700.00, ARRAY[3, 4], ARRAY[3, 1]),
    (3, 'Creada', 1800.00, ARRAY[5], ARRAY[2]);

-- Luego inserta los productos asociados en la tabla orden_producto
INSERT INTO orden_producto (orden_id, producto_ids, cantidades, precios)
VALUES
    (1, ARRAY[1, 2], ARRAY[2, 1], ARRAY[1500.00, 1200.00]),
    (2, ARRAY[3, 4], ARRAY[3, 1], ARRAY[1300.00, 1400.00]),
    (3, ARRAY[5], ARRAY[2], ARRAY[900.00]);