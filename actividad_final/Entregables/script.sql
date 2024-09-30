
CREATE TABLE public.product
(
    id serial NOT NULL,
    name text NOT NULL UNIQUE,
    price DOUBLE PRECISION NOT NULL,
    description text NOT NULL,
    image_url text NOT NULL,
    stock integer NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;


CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_product_timestamp
BEFORE UPDATE ON public.product
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

INSERT INTO public.product(
	name, price, description, image_url, stock)
	VALUES ('MacBook', 1500, 'Mac', 'image.png', 1);
INSERT INTO public.product(
	name, price, description, image_url, stock)
	VALUES ('Lenovo', 1000, 'Lenovo', 'image.png', 1);


CREATE TABLE public.user (
    id serial PRIMARY KEY,
    name text NOT NULL,
    email text NOT NULL UNIQUE,
    password text NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE IF EXISTS public.user
    OWNER to postgres;

CREATE TRIGGER update_user_timestamp
BEFORE UPDATE ON public.user
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();



CREATE TABLE public.cart (
    id serial PRIMARY KEY,
    user_id integer NOT NULL,
    total DOUBLE PRECISION DEFAULT 0,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES public.user(id)
);
ALTER TABLE IF EXISTS public.cart
    OWNER to postgres;

CREATE TRIGGER update_cart_timestamp
BEFORE UPDATE ON public.cart
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();


CREATE TABLE public.cart_product (
    id serial PRIMARY KEY,
    cart_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL CHECK (quantity > 0),
    subtotal DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES public.cart(id),
    FOREIGN KEY (product_id) REFERENCES public.product(id)
);
ALTER TABLE IF EXISTS public.cart_product
    OWNER to postgres;

CREATE OR REPLACE FUNCTION update_subtotal()
RETURNS TRIGGER AS $$
BEGIN
    NEW.subtotal = NEW.quantity * (SELECT price FROM public.product WHERE id = NEW.product_id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_cart_product_subtotal
BEFORE INSERT OR UPDATE ON public.cart_product
FOR EACH ROW
EXECUTE FUNCTION update_subtotal();

CREATE OR REPLACE FUNCTION update_cart_total()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        
        UPDATE public.cart
        SET total = (SELECT COALESCE(SUM(subtotal), 0) FROM public.cart_product WHERE cart_id = OLD.cart_id)
        WHERE id = OLD.cart_id;
    ELSE
       
        UPDATE public.cart
        SET total = (SELECT COALESCE(SUM(subtotal), 0) FROM public.cart_product WHERE cart_id = NEW.cart_id)
        WHERE id = NEW.cart_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_cart_total_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.cart_product
FOR EACH ROW
EXECUTE FUNCTION update_cart_total();



CREATE TABLE public.order (
    id serial PRIMARY KEY,
    user_id int NOT NULL,
    total DOUBLE PRECISION NOT NULL DEFAULT 0,
    status text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE public.order
    ADD CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES public.user(id);

ALTER TABLE IF EXISTS public.order
    OWNER to postgres;



CREATE TABLE public.order_product (
    order_id int NOT NULL,
    product_id int NOT NULL,
    quantity int NOT NULL,
    price double precision NOT NULL,
    subtotal double precision NOT NULL,
    PRIMARY KEY (order_id, product_id)
);

ALTER TABLE public.order_product
    ADD CONSTRAINT fk_order
    FOREIGN KEY (order_id)
    REFERENCES public.order(id);

ALTER TABLE public.order_product
    ADD CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES public.product(id);

ALTER TABLE IF EXISTS public.order_product
    OWNER to postgres;

CREATE TRIGGER update_order_timestamp
BEFORE UPDATE ON public.order
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE OR REPLACE FUNCTION update_product_stock()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
       
        UPDATE public.product
        SET stock = stock - NEW.quantity
        WHERE id = NEW.product_id;
    ELSIF TG_OP = 'UPDATE' THEN
        
        UPDATE public.product
        SET stock = stock - (NEW.quantity - OLD.quantity)
        WHERE id = NEW.product_id;
    ELSIF TG_OP = 'DELETE' THEN
    
        UPDATE public.product
        SET stock = stock + OLD.quantity
        WHERE id = OLD.product_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_product_stock
AFTER INSERT OR UPDATE OR DELETE ON public.order_product
FOR EACH ROW
EXECUTE FUNCTION update_product_stock();

CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN
    
    IF TG_OP = 'DELETE' THEN
        UPDATE public.order
        SET total = COALESCE((SELECT SUM(subtotal) FROM public.order_product WHERE order_id = OLD.order_id), 0)*1.19 + 10
        WHERE id = OLD.order_id;
    ELSIF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        UPDATE public.order
        SET total = COALESCE((SELECT SUM(subtotal) FROM public.order_product WHERE order_id = OLD.order_id), 0)*1.19 + 10
        WHERE id = OLD.order_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_order_total_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.order_product
FOR EACH ROW
EXECUTE FUNCTION update_order_total();


CREATE OR REPLACE FUNCTION update_order_product_subtotal()
RETURNS TRIGGER AS $$
BEGIN
    NEW.subtotal := NEW.quantity * NEW.price;
    RETURN NEW; 
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_order_product_subtotal_trigger
BEFORE INSERT OR UPDATE ON public.order_product
FOR EACH ROW
EXECUTE FUNCTION update_order_product_subtotal();


CREATE TABLE public.purchase_order (
    id serial NOT NULL,
    status text NOT NULL DEFAULT 'CREATED',
    total double precision NOT NULL DEFAULT 0,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE public.purchase_order_product (
    id serial NOT NULL,
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL,
    price double precision NOT NULL,
    subtotal double precision NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES public.purchase_order(id),
    FOREIGN KEY (product_id) REFERENCES public.product(id)
);

ALTER TABLE IF EXISTS public.purchase_order
    OWNER to postgres;

ALTER TABLE IF EXISTS public.purchase_order_product
    OWNER to postgres;

CREATE TRIGGER update_purchase_order_timestamp
BEFORE UPDATE ON public.purchase_order
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE OR REPLACE FUNCTION purchase_update_product_stock()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE public.product
        SET stock = stock + NEW.quantity
        WHERE id = NEW.product_id;

    ELSIF TG_OP = 'DELETE' THEN
        UPDATE public.product
        SET stock = stock - OLD.quantity
        WHERE id = OLD.product_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_product_stock_trigger
AFTER INSERT OR DELETE ON public.purchase_order_product
FOR EACH ROW
EXECUTE FUNCTION purchase_update_product_stock();


CREATE OR REPLACE FUNCTION update_purchase_order_total()
RETURNS TRIGGER AS $$
BEGIN
    
    IF TG_OP = 'DELETE' THEN
        UPDATE public.purchase_order
        SET total = COALESCE((SELECT SUM(subtotal) FROM public.purchase_order_product WHERE order_id = OLD.order_id), 0)
        WHERE id = OLD.order_id;

    
    ELSIF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        UPDATE public.purchase_order
        SET total = COALESCE((SELECT SUM(subtotal) FROM public.purchase_order_product WHERE order_id = NEW.order_id), 0)
        WHERE id = NEW.order_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_purchase_order_total_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.purchase_order_product
FOR EACH ROW
EXECUTE FUNCTION update_purchase_order_total();


CREATE OR REPLACE FUNCTION update_purchase_order_product_subtotal()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        NEW.subtotal := NEW.quantity * NEW.price;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_purchase_order_product_subtotal_trigger
BEFORE INSERT OR UPDATE ON public.purchase_order_product
FOR EACH ROW
EXECUTE FUNCTION update_purchase_order_product_subtotal();
