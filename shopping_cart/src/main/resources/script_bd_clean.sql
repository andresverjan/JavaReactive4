CREATE SEQUENCE IF NOT EXISTS cart_item_id_seq;
CREATE TABLE IF NOT EXISTS cart_item (
  id integer NOT NULL DEFAULT nextval('cart_item_id_seq') PRIMARY KEY,
  cart_id integer,
  product_id integer,
  quantity integer,
  price_unit numeric,
  total_item_price numeric,
  added_at timestamp without time zone
);
CREATE UNIQUE INDEX IF NOT EXISTS cart_item_pkey ON cart_item (id);

CREATE SEQUENCE IF NOT EXISTS clients_id_seq;
CREATE TABLE IF NOT EXISTS clients (
  email character varying NOT NULL,
  name character varying NOT NULL,
  phone character varying NOT NULL,
  id integer NOT NULL DEFAULT nextval('clients_id_seq') PRIMARY KEY
);
CREATE UNIQUE INDEX IF NOT EXISTS clients_pkey ON clients (id);
CREATE UNIQUE INDEX IF NOT EXISTS uq_email_client ON clients (email);

CREATE SEQUENCE IF NOT EXISTS products_id_seq;
CREATE TABLE IF NOT EXISTS products (
  id integer NOT NULL DEFAULT nextval('products_id_seq') PRIMARY KEY,
  name character varying,
  price numeric,
  description character varying,
  image_url character varying,
  stock integer,
  created_at timestamp without time zone,
  update_at timestamp without time zone
);
CREATE UNIQUE INDEX IF NOT EXISTS products_pkey ON products (id);

CREATE SEQUENCE IF NOT EXISTS purchase_item_id_seq;
CREATE TABLE IF NOT EXISTS purchase_item (
  id integer NOT NULL DEFAULT nextval('purchase_item_id_seq') PRIMARY KEY,
  purchase_order_id integer NOT NULL,
  quantity integer NOT NULL,
  price_unit numeric NOT NULL,
  total_item_price numeric NOT NULL,
  added_at timestamp without time zone NOT NULL,
  update_at timestamp without time zone,
  product_id integer NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS purchase_item_pkey ON purchase_item (id);

CREATE SEQUENCE IF NOT EXISTS purchase_order_id_seq;
CREATE TABLE IF NOT EXISTS purchase_order (
  id integer NOT NULL DEFAULT nextval('purchase_order_id_seq') PRIMARY KEY,
  seller character varying NOT NULL,
  transport_cost numeric NOT NULL,
  status character varying NOT NULL,
  total numeric NOT NULL,
  created_at timestamp without time zone NOT NULL,
  update_at timestamp without time zone
);
CREATE UNIQUE INDEX IF NOT EXISTS purchase_order_pkey ON purchase_order (id);

CREATE SEQUENCE IF NOT EXISTS sales_orders_id_seq;
CREATE TABLE IF NOT EXISTS sales_orders (
  id integer NOT NULL DEFAULT nextval('sales_orders_id_seq') PRIMARY KEY,
  client_id integer NOT NULL,
  shopping_cart_id integer NOT NULL,
  shipping_address character varying NOT NULL,
  taxes numeric NOT NULL,
  status character varying NOT NULL,
  total numeric NOT NULL,
  shipping_cost numeric NOT NULL,
  created_at timestamp without time zone NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS sales_orders_pkey ON sales_orders (id);

CREATE SEQUENCE IF NOT EXISTS shopping_cart_id_seq;
CREATE TABLE IF NOT EXISTS shopping_cart (
  id integer NOT NULL DEFAULT nextval('shopping_cart_id_seq') PRIMARY KEY,
  client_id integer NOT NULL,
  status character varying NOT NULL,
  total_price numeric NOT NULL,
  created_at timestamp without time zone,
  update_at timestamp without time zone
);
CREATE UNIQUE INDEX IF NOT EXISTS shopping_cart_pkey ON shopping_cart (id);

ALTER TABLE cart_item ADD CONSTRAINT FK_cart_id FOREIGN KEY (cart_id) REFERENCES shopping_cart (id);
ALTER TABLE shopping_cart ADD CONSTRAINT FK_client_id FOREIGN KEY (client_id) REFERENCES clients (id);
ALTER TABLE sales_orders ADD CONSTRAINT FK_client_id FOREIGN KEY (client_id) REFERENCES clients (id);
ALTER TABLE purchase_item ADD CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE cart_item ADD CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE purchase_item ADD CONSTRAINT FK_purchase_order FOREIGN KEY (purchase_order_id) REFERENCES purchase_order (id);
ALTER TABLE sales_orders ADD CONSTRAINT FK_shopping_cart_id FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);
