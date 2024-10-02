CREATE SCHEMA commerce;

CREATE TABLE commerce.cart (
    id serial4 NOT NULL,
    created_at timestamp DEFAULT current_timestamp,
    updated_at timestamp DEFAULT current_timestamp,
    CONSTRAINT cart_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE commerce.cartproduct_id_seq;

CREATE TABLE commerce.cart_product (
    id int4 DEFAULT nextval('commerce.cartproduct_id_seq') NOT NULL,
    quantity int4 NOT NULL,
    cart_id int4 NOT NULL,
    product_id int4 NOT NULL,
    CONSTRAINT cartproduct_pkey PRIMARY KEY (id),
    CONSTRAINT cartproduct_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES commerce.cart(id),
    CONSTRAINT cartproduct_product_id_fkey FOREIGN KEY (product_id) REFERENCES commerce.product(id)
);

CREATE TABLE commerce.product (
    id serial4 NOT NULL,
    name varchar(255) NOT NULL,
    price float8 NOT NULL,
    description text,
    image_url varchar(255),
    stock int4 NOT NULL,
    created_at timestamp DEFAULT current_timestamp,
    updated_at timestamp DEFAULT current_timestamp,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);


CREATE SEQUENCE commerce.purchaseorder_id_seq;

CREATE TABLE commerce.purchase_order (
    id int4 DEFAULT nextval('commerce.purchaseorder_id_seq') NOT NULL,
    total float8 NOT NULL,
    state varchar(50) NOT NULL,
    created_at timestamp DEFAULT current_timestamp,
    updated_at timestamp DEFAULT current_timestamp,
    CONSTRAINT purchaseorder_pkey PRIMARY KEY (id)
);


CREATE SEQUENCE commerce.purchaseorderproduct_id_seq;

CREATE TABLE commerce.purchase_order_product (
    id int4 DEFAULT nextval('commerce.purchaseorderproduct_id_seq') NOT NULL,
    purchase_order_id int4 NOT NULL,
    product_id int4 NOT NULL,
    quantity int4 NOT NULL,
    CONSTRAINT purchaseorderproduct_pkey PRIMARY KEY (id),
    CONSTRAINT purchaseorderproduct_purchase_order_id_fkey FOREIGN KEY (purchase_order_id) REFERENCES commerce.purchase_order(id),
    CONSTRAINT purchaseorderproduct_product_id_fkey FOREIGN KEY (product_id) REFERENCES commerce.product(id)
);


CREATE SEQUENCE commerce.saleorder_id_seq;

CREATE TABLE commerce.sale_order (
    id int4 DEFAULT nextval('commerce.saleorder_id_seq') NOT NULL,
    total float8 NOT NULL,
    state varchar(50) NOT NULL,
    created_at timestamp DEFAULT current_timestamp,
    updated_at timestamp DEFAULT current_timestamp,
    CONSTRAINT saleorder_pkey PRIMARY KEY (id)
);


CREATE SEQUENCE commerce.saleorderproduct_id_seq;

CREATE TABLE commerce.sale_order_product (
    id int4 DEFAULT nextval('commerce.saleorderproduct_id_seq') NOT NULL,
    sale_order_id int4 NOT NULL,
    product_id int4 NOT NULL,
    quantity int4 NOT NULL,
    CONSTRAINT saleorderproduct_pkey PRIMARY KEY (id),
    CONSTRAINT saleorderproduct_sale_order_id_fkey FOREIGN KEY (sale_order_id) REFERENCES commerce.sale_order(id),
    CONSTRAINT saleorderproduct_product_id_fkey FOREIGN KEY (product_id) REFERENCES commerce.product(id)
);

select * from commerce.product;