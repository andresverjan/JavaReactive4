-- Database: shoppingcart

-- DROP DATABASE IF EXISTS shoppingcart;

CREATE DATABASE shoppingcart
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE shoppingcart
    IS 'shoppingcart';

-- Table: public.products

-- DROP TABLE IF EXISTS public.products;

CREATE TABLE IF NOT EXISTS public.products
(
    id integer NOT NULL DEFAULT nextval('"Product_id_seq"'::regclass),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price numeric(14,2) NOT NULL,
    description character varying(500) COLLATE pg_catalog."default",
    imageurl character varying(500) COLLATE pg_catalog."default",
    stock numeric(14,2) NOT NULL DEFAULT 0,
    taxpercentage numeric(3,2) NOT NULL,
    discountpercentage numeric(3,2) NOT NULL,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT "Product_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;

-- Trigger: update_products_updated_on

-- DROP TRIGGER IF EXISTS update_products_updated_on ON public.products;

CREATE OR REPLACE TRIGGER update_products_updated_on
    BEFORE UPDATE 
    ON public.products
    FOR EACH ROW
    EXECUTE FUNCTION public.update_updated_on_products();
	
-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    is_seller boolean DEFAULT false,
    is_buyer boolean DEFAULT true,
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT users_pkey PRIMARY KEY (id, username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

-- Table: public.cart_master

-- DROP TABLE IF EXISTS public.cart_master;

CREATE TABLE IF NOT EXISTS public.cart_master
(
    id integer NOT NULL DEFAULT nextval('"Cart_Master_id_seq"'::regclass),
    orderid integer,
    subtotalorder numeric(14,2) NOT NULL,
    totaldiscount numeric(14,2) NOT NULL,
    totaltax numeric(14,2) NOT NULL,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    userid integer NOT NULL,
    CONSTRAINT "Cart_Master_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cart_master
    OWNER to postgres;

-- Trigger: update_cart_master_updated_on

-- DROP TRIGGER IF EXISTS update_cart_master_updated_on ON public.cart_master;

CREATE OR REPLACE TRIGGER update_cart_master_updated_on
    BEFORE UPDATE 
    ON public.cart_master
    FOR EACH ROW
    EXECUTE FUNCTION public.update_updated_on_cart_master();

-- Table: public.cart_detail

-- DROP TABLE IF EXISTS public.cart_detail;

CREATE TABLE IF NOT EXISTS public.cart_detail
(
    id integer NOT NULL DEFAULT nextval('"Cart_Detail_id_seq"'::regclass),
    cartid integer,
    productid integer NOT NULL,
    amount numeric(14,0) NOT NULL DEFAULT 0,
    price numeric(14,2) NOT NULL,
    amountdiscount numeric(14,2) NOT NULL DEFAULT 0,
    amounttax numeric(14,2) NOT NULL DEFAULT 0,
    shippingcost numeric(14,2) NOT NULL DEFAULT 0,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT "Cart_Detail_pkey" PRIMARY KEY (id),
    CONSTRAINT fk_ord_id_cart FOREIGN KEY (cartid)
        REFERENCES public.cart_master (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cart_detail
    OWNER to postgres;

-- Table: public.sales_order_master

-- DROP TABLE IF EXISTS public.sales_order_master;

CREATE TABLE IF NOT EXISTS public.sales_order_master
(
    id integer NOT NULL DEFAULT nextval('"Sales_Order_Master_id_seq"'::regclass),
    subtotalorder numeric(14,2) NOT NULL,
    totaldiscount numeric(14,2) NOT NULL,
    totaltax numeric(14,2) NOT NULL,
    iscancelled boolean NOT NULL DEFAULT false,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    userid integer,
    CONSTRAINT "Sales_Order_Master_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sales_order_master
    OWNER to postgres;

-- Table: public.sales_order_detail

-- DROP TABLE IF EXISTS public.sales_order_detail;

CREATE TABLE IF NOT EXISTS public.sales_order_detail
(
    id integer NOT NULL DEFAULT nextval('"Sales_Order_Detail_id_seq"'::regclass),
    orderid integer NOT NULL,
    productid integer NOT NULL,
    amount numeric(14,0) NOT NULL DEFAULT 0,
    "salesPrice" numeric(14,2) NOT NULL,
    amountdiscount numeric(14,2) NOT NULL DEFAULT 0,
    amounttax numeric(14,2) NOT NULL DEFAULT 0,
    createdat timestamp without time zone NOT NULL DEFAULT now(),
    updatedat timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT "Sales_Order_Detail_pkey" PRIMARY KEY (id),
    CONSTRAINT sales_order_detail_orderid_fkey FOREIGN KEY (orderid)
        REFERENCES public.sales_order_master (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT sales_order_detail_productid_fkey FOREIGN KEY (productid)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sales_order_detail
    OWNER to postgres;