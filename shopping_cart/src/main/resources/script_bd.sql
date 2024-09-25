-- usuario de conexion--
CREATE ROLE usuariocnx WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';

-- base de datos --
CREATE DATABASE shopping_cart_api
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT CONNECT ON DATABASE shopping_cart_api TO usuariocnx;

--secuencia id
-- SEQUENCE: public.products_id_seq

-- DROP SEQUENCE IF EXISTS public.products_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.products_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.products_id_seq
    OWNED BY public.products.id;

ALTER SEQUENCE public.products_id_seq
    OWNER TO postgres;

GRANT ALL ON SEQUENCE public.products_id_seq TO postgres;

GRANT SELECT, USAGE ON SEQUENCE public.products_id_seq TO usuariocnx;

-- tabla productos
-- Table: public.products

-- DROP TABLE IF EXISTS public.products;

CREATE TABLE IF NOT EXISTS public.products
(
    id integer NOT NULL DEFAULT nextval('products_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    price numeric,
    description character varying COLLATE pg_catalog."default",
    image_url character varying COLLATE pg_catalog."default",
    stock integer,
    created_at timestamp without time zone,
    update_at timestamp without time zone,
    CONSTRAINT products_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;

REVOKE ALL ON TABLE public.products FROM usuariocnx;

GRANT ALL ON TABLE public.products TO postgres;

GRANT DELETE, UPDATE, INSERT, TRUNCATE, SELECT ON TABLE public.products TO usuariocnx;

-- permiso del usuario para usar la secuencia--
GRANT SELECT, USAGE ON SEQUENCE public.products_id_seq TO usuariocnx;

-- tabla clientes
CREATE TABLE IF NOT EXISTS public.clients
(
    email character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    phone character varying COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL DEFAULT nextval('clients_id_seq'::regclass),
    CONSTRAINT clients_pkey PRIMARY KEY (id),
    CONSTRAINT uq_email_client UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clients
    OWNER to postgres;

REVOKE ALL ON TABLE public.clients FROM usuariocnx;

GRANT ALL ON TABLE public.clients TO postgres;

GRANT DELETE, UPDATE, INSERT, TRUNCATE, SELECT ON TABLE public.clients TO usuariocnx;

--permisos de usuario de cx en la secuencia de clientes
GRANT SELECT, USAGE ON SEQUENCE public.clients_id_seq TO usuariocnx;

--tabla shoping_cart
CREATE TABLE IF NOT EXISTS public.shopping_cart
(
    id integer NOT NULL DEFAULT nextval('shopping_cart_id_seq'::regclass),
    client_id integer NOT NULL,
    created_at time without time zone NOT NULL,
    update_at time without time zone,
    status character varying NOT NULL,
    total_price numeric NOT NULL,
    CONSTRAINT shopping_cart_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_client_id" FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.shopping_cart
    OWNER to postgres;

REVOKE ALL ON TABLE public.shopping_cart FROM usuariocnx;

GRANT ALL ON TABLE public.shopping_cart TO postgres;

GRANT DELETE, UPDATE, INSERT, SELECT ON TABLE public.shopping_cart TO usuariocnx;

--permisos de usuario en secuencia de carrito de compras
GRANT SELECT, USAGE ON SEQUENCE public.shopping_cart_id_seq TO usuariocnx;

--tabla item carrito
CREATE TABLE IF NOT EXISTS public.cart_item
(
    id integer NOT NULL DEFAULT nextval('cart_item_id_seq'::regclass),
    cart_id integer,
    product_id integer,
    quantity integer,
    price_unit numeric,
    total_item_price numeric,
    added_at timestamp without time zone,
    CONSTRAINT cart_item_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_cart_id" FOREIGN KEY (cart_id)
        REFERENCES public.shopping_cart (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_product_id" FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cart_item
    OWNER to postgres;

REVOKE ALL ON TABLE public.cart_item FROM usuariocnx;

GRANT ALL ON TABLE public.cart_item TO postgres;

GRANT DELETE, UPDATE, INSERT, TRUNCATE, SELECT ON TABLE public.cart_item TO usuariocnx;

--permisos de usuario para item carrito (secuencia)
GRANT SELECT, USAGE ON SEQUENCE public.cart_item_id_seq TO usuariocnx;

--tabla de ordenes de venta
CREATE TABLE IF NOT EXISTS public.sales_orders
(
    id integer NOT NULL DEFAULT nextval('sales_orders_id_seq'::regclass),
    client_id integer NOT NULL,
    shopping_cart_id integer NOT NULL,
    shipping_address character varying COLLATE pg_catalog."default" NOT NULL,
    taxes numeric NOT NULL,
    status character varying COLLATE pg_catalog."default" NOT NULL,
    total numeric NOT NULL,
    shipping_cost numeric NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT sales_orders_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_client_id" FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "FK_shopping_cart_id" FOREIGN KEY (shopping_cart_id)
        REFERENCES public.shopping_cart (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sales_orders
    OWNER to postgres;

REVOKE ALL ON TABLE public.sales_orders FROM usuariocnx;

GRANT ALL ON TABLE public.sales_orders TO postgres;

GRANT DELETE, UPDATE, INSERT, SELECT ON TABLE public.sales_orders TO usuariocnx;
--permisos para usarr secuencia de ordenes de venta
GRANT SELECT, USAGE ON SEQUENCE public.sales_orders_id_seq TO usuariocnx;

---tabla ordenes de compra
CREATE TABLE public.purchase_order
(
    id serial,
    seller character varying NOT NULL,
    transport_cost numeric NOT NULL,
    status character varying NOT NULL,
    total numeric NOT NULL,
    created_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.purchase_order
    OWNER to postgres;

GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE ON TABLE public.purchase_order TO usuariocnx;

--permisos e secuencia de ordenes de venta

GRANT SELECT, USAGE ON SEQUENCE public.purchase_order_id_seq TO usuariocnx;

--tabla de itemes de orden de compra
CREATE TABLE public.purchase_item
(
    id serial,
    product_id integer NOT NULL,
    purchase_order_id integer NOT NULL,
    quantity integer NOT NULL,
    price_unit numeric NOT NULL,
    total_item_price numeric NOT NULL,
    added_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone,
    PRIMARY KEY (id),
    CONSTRAINT "FK_purchase_order" FOREIGN KEY (purchase_order_id)
        REFERENCES public.purchase_order (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "FK_product_id" FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.purchase_item
    OWNER to postgres;

GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE ON TABLE public.purchase_item TO usuariocnx;
--permisos de uso de secuencia de items de compra
GRANT SELECT, USAGE ON SEQUENCE public.purchase_item_id_seq TO usuariocnx;