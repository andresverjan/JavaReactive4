
CREATE TABLE IF NOT EXISTS public.tbl_product (
	id serial NOT NULL,
	created_date timestamp NULL,
    updated_date timestamp NULL,
	name character varying(255) NULL,
	code character varying(255) NULL,
	price double precision NULL,
    description text NULL,
    image_url character varying(255) NULL,
    stock integer, 
	CONSTRAINT tbl_product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tbl_user (
	id serial NOT NULL,
	created_date timestamp NULL,
    updated_date timestamp NULL,
	name character varying(255) NULL,
	document_number character varying(255) NULL,
	email character varying(255) NULL,
	cellphone character varying(255) NULL,
	CONSTRAINT uk1_field UNIQUE (document_number),
	CONSTRAINT tbl_user_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.tbl_shopping_car (
	id serial NOT NULL,
	created_date timestamp NULL,
    updated_date timestamp NULL,
    user_id integer not null,
    CONSTRAINT tbl_shopping_car_pkey PRIMARY KEY (id),
	CONSTRAINT fk1_shopping_car FOREIGN KEY (user_id)
        REFERENCES public.tbl_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.tbl_product_car (
	id serial NOT NULL,
	created_date timestamp NULL,
    updated_date timestamp NULL,
    shopping_car_id integer not null, 
    product_id integer not null,
    price double precision NULL,
    quantity integer not null,
    CONSTRAINT tbl_product_car_pkey PRIMARY KEY (id),
    CONSTRAINT uk1_product_car  UNIQUE (shopping_car_id, product_id),
	CONSTRAINT fk1_product_car FOREIGN KEY (shopping_car_id)
        REFERENCES public.tbl_shopping_car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO action,
   	CONSTRAINT fk2_product_car FOREIGN KEY (product_id)
        REFERENCES public.tbl_product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO action     
);

