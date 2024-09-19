CREATE OR REPLACE FUNCTION tienda.generar_producto_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'PR_' || nextval('tienda.producto_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_producto_id
BEFORE INSERT ON tienda.Productos
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_producto_id();



CREATE OR REPLACE FUNCTION tienda.generar_carrito_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'CA_' || nextval('tienda.carrito_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_carrito_id
BEFORE INSERT ON tienda.CarritoCompras
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_carrito_id();



CREATE OR REPLACE FUNCTION tienda.generar_orden_venta_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'OV_' || nextval('tienda.orden_venta_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_orden_venta_id
BEFORE INSERT ON tienda.OrdenesVentas
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_orden_venta_id();