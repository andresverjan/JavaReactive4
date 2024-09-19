INSERT INTO tienda.OrdenesVentasProductos (orden_id, producto_id, cantidad, precio_unitario)
VALUES ('OV_1', 'PR_1', 2, 1500.00); -- La orden 'OV_1' contiene 2 unidades del producto 'PR_1' a 1500.00 cada uno


INSERT INTO tienda.OrdenesVentas (usuario_id, total, estado)
VALUES ('1234567890', 3000.00, 'Creada'); -- Orden de venta creada por el usuario '1234567890'


INSERT INTO tienda.CarritoProductos (carrito_id, producto_id, cantidad)
VALUES ('CA_1', 'PR_1', 2); -- El carrito 'CA_1' contiene 2 unidades del producto 'PR_1'


INSERT INTO tienda.CarritoCompras (usuario_id)
VALUES ('1234567890'); -- El usuario con ID '1234567890' ha creado un carrito


INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('MacBook Pro', 1500.00, 'Laptop Apple', 'macbook.png', 10); -- Producto 'MacBook Pro' con 10 unidades en stock


INSERT INTO tienda.Usuarios(id, nombre, email,)
VALUES ('1234567890', 'Daniela Guzman', 'dani.guzman@gmail.com'); -- Usuario con ID '1234567890'