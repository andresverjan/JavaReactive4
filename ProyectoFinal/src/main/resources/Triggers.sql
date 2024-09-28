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



CREATE OR REPLACE FUNCTION tienda.generar_orden_compra_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'OC_' || nextval('tienda.orden_compra_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_orden_compra_id
BEFORE INSERT ON tienda.OrdenesCompras
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_orden_compra_id();



CREATE OR REPLACE FUNCTION tienda.generar_empresa_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'EM_' || nextval('tienda.empresa_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_empresa_id
BEFORE INSERT ON tienda.Empresas
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_empresa_id();



CREATE OR REPLACE FUNCTION tienda.generar_carrito_producto_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'CP_' || nextval('tienda.carrito_producto_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_generar_carrito_producto_id
BEFORE INSERT ON tienda.CarritoProducto
FOR EACH ROW
EXECUTE FUNCTION tienda.generar_carrito_producto_id();



CREATE OR REPLACE FUNCTION tienda.orden_venta_producto_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'OVP_' || nextval('tienda.orden_venta_producto_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_orden_venta_producto_id
BEFORE INSERT ON tienda.OrdenVentaProducto
FOR EACH ROW
EXECUTE FUNCTION tienda.orden_venta_producto_id();



CREATE OR REPLACE FUNCTION tienda.vendedor_empresa_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'VE_' || nextval('tienda.vendedor_empresa_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_vendedor_empresa_id
BEFORE INSERT ON tienda.VendedorEmpresa
FOR EACH ROW
EXECUTE FUNCTION tienda.vendedor_empresa_id();



CREATE OR REPLACE FUNCTION tienda.orden_compra_producto_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.id := 'OCP_' || nextval('tienda.orden_compra_producto_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_orden_compra_producto_id
BEFORE INSERT ON tienda.OrdenCompraProducto
FOR EACH ROW
EXECUTE FUNCTION tienda.orden_compra_producto_id();