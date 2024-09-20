INSERT INTO tienda.Usuarios (id, nombre, email, rol)
VALUES ('1234567890', 'Daniela Guzman', 'dani.guzman@email.com', 'Cliente'); -- Usuario 'Daniela Guzman' con rol de Cliente

INSERT INTO tienda.Usuarios (id, nombre, email, rol)
VALUES ('1850275937', 'Juan Perez', 'juan.perez@email.com', 'Vendedor'); -- Usuario 'Juan Perez' con rol de Vendedor

INSERT INTO tienda.Usuarios (id, nombre, email, rol)
VALUES ('7196847107', 'Maria Lopez', 'maria.lopez@email.com', 'Ambos'); -- Usuario 'Maria Lopez' con rol de Ambos



INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('MacBook Pro', 1500.00, 'Laptop Apple', 'macbook.png', 10); -- Producto 'MacBook Pro' con 10 unidades en stock

INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('iPhone 12', 999.00, 'iPhone 12 64GB', 'iphone12.png', 25); -- Producto 'iPhone 12' con 25 unidades en stock

INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('Apple Watch', 399.00, 'Apple Watch Series 6', 'apple_watch.png', 15); -- Producto 'Apple Watch' con 15 unidades en stock

INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('iPad Pro', 1099.00, 'iPad Pro 11 pulgadas', 'ipad_pro.png', 5); -- Producto 'iPad Pro' con 5 unidades en stock

INSERT INTO tienda.Productos (nombre, precio, descripcion, imagen_url, stock)
VALUES ('AirPods Pro', 249.00, 'AirPods Pro con estuche de carga inalámbrica', 'airpods_pro.png', 50); -- Producto 'AirPods Pro' con 50 unidades en stock



INSERT INTO tienda.CarritoCompras (usuario_id)
VALUES ('1234567890'); -- El usuario con ID '1234567890' ha creado un carrito

INSERT INTO tienda.CarritoCompras (usuario_id)
VALUES ('7196847107'); -- El usuario con ID '7196847107' ha creado un carrito



INSERT INTO tienda.CarritoProductos (carrito_id, producto_id, cantidad)
VALUES ('CA_1', 'PR_1', 1); -- El carrito 'CA_1' contiene 1 unidades del producto 'PR_1'

INSERT INTO tienda.CarritoProductos (carrito_id, producto_id, cantidad)
VALUES ('CA_1', 'PR_2', 1); -- El carrito 'CA_1' contiene 1 unidades del producto 'PR_2'

INSERT INTO tienda.CarritoProductos (carrito_id, producto_id, cantidad)
VALUES ('CA_1', 'PR_5', 2); -- El carrito 'CA_1' contiene 2 unidades del producto 'PR_5'

INSERT INTO tienda.CarritoProductos (carrito_id, producto_id, cantidad)
VALUES ('CA_2', 'PR_3', 2); -- El carrito 'CA_2' contiene 2 unidades del producto 'PR_3'



INSERT INTO tienda.OrdenesVentas (usuario_id, total, estado)
VALUES ('1234567890', 2997.00, 'Creada'); -- Orden de venta creada por el usuario '1234567890'

INSERT INTO tienda.OrdenesVentas (usuario_id, total, estado)
VALUES ('7196847107', 798.00, 'Creada'); -- Orden de venta creada por el usuario '7196847107'



INSERT INTO tienda.OrdenesVentasProductos (orden_id, producto_id, cantidad, precio_unitario)
VALUES ('OV_1', 'PR_1', 1, 1500.00); -- La orden 'OV_1' contiene 1 unidad del producto 'PR_1' a 1500.00 cada uno

INSERT INTO tienda.OrdenesVentasProductos (orden_id, producto_id, cantidad, precio_unitario)
VALUES ('OV_1', 'PR_2', 1, 999.00); -- La orden 'OV_1' contiene 1 unidad del producto 'PR_2' a 999.00 cada uno

INSERT INTO tienda.OrdenesVentasProductos (orden_id, producto_id, cantidad, precio_unitario)
VALUES ('OV_1', 'PR_5', 2, 249.00); -- La orden 'OV_1' contiene 2 unidades del producto 'PR_5' a 249.00 cada uno

INSERT INTO tienda.OrdenesVentasProductos (orden_id, producto_id, cantidad, precio_unitario)
VALUES ('OV_2', 'PR_3', 2, 399.00); -- La orden 'OV_2' contiene 2 unidades del producto 'PR_3' a 399.00 cada uno



INSERT INTO tienda.Empresas (nombre, direccion, telefono)
VALUES ('Apple Tech', '123 Main St', '555-1234');



INSERT INTO tienda.VendedoresEmpresas (vendedor_id, empresa_id)
VALUES ('1850275937', 'EM_1'); -- Juan Pérez es vendedor de Apple Tech



INSERT INTO tienda.OrdenesCompras (empresa_id, vendedor_id, total, created_at, updated_at)
VALUES ('EM_1', '1850275937', 5495.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Inserción para una orden de compra hecha a la empresa EMP_1, con el vendedor 1850275937



INSERT INTO tienda.OrdenesComprasProductos (orden_id, producto_id, cantidad, precio_unitario, created_at)
VALUES ('OC_1', 'PR_4', 5, 1099.00, CURRENT_TIMESTAMP); -- Compra de 10 iPads Pro