-- Tabla para productos
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    stock INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- Tabla para usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- Tabla para carritos de compra (relacionado con usuarios)
CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- Tabla para items del carrito (CartItem), relacionado con carritos y productos
CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL REFERENCES carts(id),
    product_id INTEGER NOT NULL REFERENCES products(id),
    amount INTEGER NOT NULL
);

-- Tabla para las órdenes de compra (PurchaseOrder)
CREATE TABLE purchase_orders (
    id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    total DOUBLE PRECISION NOT NULL
);

-- Tabla de productos para las órdenes de compra (relación de muchos a muchos)
CREATE TABLE purchase_order_products (
    purchase_order_id INTEGER NOT NULL REFERENCES purchase_orders(id),
    product_id INTEGER NOT NULL REFERENCES products(id),
	amount INTEGER NOT NULL,
    PRIMARY KEY (purchase_order_id, product_id)
);

-- Tabla para las órdenes de venta (SaleOrder)
CREATE TABLE sale_orders (
    id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id),
    creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    total DOUBLE PRECISION NOT NULL
);

-- Tabla de productos para las órdenes de venta (relación de muchos a muchos)
CREATE TABLE sale_order_products (
    sale_order_id INTEGER NOT NULL REFERENCES sale_orders(id),
    product_id INTEGER NOT NULL REFERENCES products(id),
	amount INTEGER NOT NULL,
    PRIMARY KEY (sale_order_id, product_id)
);
