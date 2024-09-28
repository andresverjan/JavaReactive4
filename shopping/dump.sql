--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Postgres.app)
-- Dumped by pg_dump version 16.4 (Postgres.app)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: shoppingcartstate; Type: TYPE; Schema: public; Owner: reactiveuser
--

CREATE TYPE public.shoppingcartstate AS ENUM (
    'R',
    'A',
    'S'
);


ALTER TYPE public.shoppingcartstate OWNER TO reactiveuser;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: reactiveuser
--

CREATE TABLE public.client (
    id character varying(15) NOT NULL,
    name character varying(30) NOT NULL,
    lastname character varying(30) NOT NULL,
    address character varying(50) NOT NULL,
    telephone character varying(15) NOT NULL,
    date_of_birth date
);


ALTER TABLE public.client OWNER TO reactiveuser;

--
-- Name: products; Type: TABLE; Schema: public; Owner: reactiveuser
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    stock integer NOT NULL,
    price numeric(10,2) NOT NULL
);


ALTER TABLE public.products OWNER TO reactiveuser;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: reactiveuser
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO reactiveuser;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: reactiveuser
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- Name: sales; Type: TABLE; Schema: public; Owner: reactiveuser
--

CREATE TABLE public.sales (
    id integer NOT NULL,
    product integer NOT NULL,
    client character varying(15) NOT NULL,
    cartitemid integer NOT NULL,
    price numeric(10,2) NOT NULL,
    state character varying(1) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.sales OWNER TO reactiveuser;

--
-- Name: sales_id_seq; Type: SEQUENCE; Schema: public; Owner: reactiveuser
--

CREATE SEQUENCE public.sales_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sales_id_seq OWNER TO reactiveuser;

--
-- Name: sales_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: reactiveuser
--

ALTER SEQUENCE public.sales_id_seq OWNED BY public.sales.id;


--
-- Name: shoppingcart; Type: TABLE; Schema: public; Owner: reactiveuser
--

CREATE TABLE public.shoppingcart (
    id integer NOT NULL,
    buyer character varying(15) NOT NULL,
    quantity integer NOT NULL,
    status character varying(1) NOT NULL,
    product integer NOT NULL
);


ALTER TABLE public.shoppingcart OWNER TO reactiveuser;

--
-- Name: shoppingcart_id_seq; Type: SEQUENCE; Schema: public; Owner: reactiveuser
--

CREATE SEQUENCE public.shoppingcart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.shoppingcart_id_seq OWNER TO reactiveuser;

--
-- Name: shoppingcart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: reactiveuser
--

ALTER SEQUENCE public.shoppingcart_id_seq OWNED BY public.shoppingcart.id;


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: sales id; Type: DEFAULT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.sales ALTER COLUMN id SET DEFAULT nextval('public.sales_id_seq'::regclass);


--
-- Name: shoppingcart id; Type: DEFAULT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.shoppingcart ALTER COLUMN id SET DEFAULT nextval('public.shoppingcart_id_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: reactiveuser
--

COPY public.client (id, name, lastname, address, telephone, date_of_birth) FROM stdin;
1085444	juan	Perez	carrera-40-23	123345	1995-01-01
1185444	juan	Perez	carrera-40-23	123345	1995-01-01
1085445	Francisco	Garcia	calle-40-23	1002003	1982-01-01
12345678	Carlos	Guzman	carrera-40-23	123345	1995-01-01
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: reactiveuser
--

COPY public.products (id, name, stock, price) FROM stdin;
4	computer	40	800.00
2	Frezzer	12	2500.00
6	desktop	95	1200.00
1	TV	18	4400.00
3	radio	44	100.00
\.


--
-- Data for Name: sales; Type: TABLE DATA; Schema: public; Owner: reactiveuser
--

COPY public.sales (id, product, client, cartitemid, price, state, created_at) FROM stdin;
12	3	1185444	17	103.00	S	2024-01-01 00:30:30
13	2	1185444	18	2505.00	S	2024-01-01 00:30:30
14	2	1185444	19	12500.00	S	2024-01-01 00:30:30
15	2	1185444	20	12500.00	S	2024-01-01 00:30:30
16	2	1185444	21	12500.00	S	2024-01-01 00:30:30
7	4	1185444	12	802.00	S	2024-01-02 00:30:30
8	3	1185444	13	106.00	S	2024-01-02 00:30:30
9	1	1185444	14	4401.00	S	2024-01-02 00:30:30
10	1	1185444	15	4401.00	S	2024-01-03 01:30:30
11	1	1185444	16	4401.00	S	2024-01-03 01:30:30
17	3	12345678	25	300.00	S	2024-01-04 01:30:30
18	2	12345678	26	5000.00	S	2024-01-05 01:30:30
19	6	12345678	28	1200.00	S	2024-01-05 01:30:30
\.


--
-- Data for Name: shoppingcart; Type: TABLE DATA; Schema: public; Owner: reactiveuser
--

COPY public.shoppingcart (id, buyer, quantity, status, product) FROM stdin;
1	1085445	3	S	1
2	1085445	3	S	2
3	1085445	3	S	2
4	1085445	3	S	1
5	1085445	3	S	1
6	1085445	3	S	2
7	1185444	8	S	2
8	1185444	3	S	1
9	1185444	2	R	4
11	1185444	2	R	4
10	1185444	3	R	3
14	1185444	1	S	1
12	1185444	2	S	4
13	1185444	6	S	3
15	1185444	1	S	1
16	1185444	1	S	1
17	1185444	3	S	3
18	1185444	5	S	2
19	1185444	5	S	2
20	1185444	5	S	2
21	1185444	5	S	2
22	12345678	5	S	6
23	12345678	2	S	1
24	12345678	3	S	3
25	12345678	3	S	3
28	12345678	1	S	6
26	12345678	2	S	2
29	12345678	1	R	6
30	12345678	1	R	4
\.


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: reactiveuser
--

SELECT pg_catalog.setval('public.products_id_seq', 6, true);


--
-- Name: sales_id_seq; Type: SEQUENCE SET; Schema: public; Owner: reactiveuser
--

SELECT pg_catalog.setval('public.sales_id_seq', 19, true);


--
-- Name: shoppingcart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: reactiveuser
--

SELECT pg_catalog.setval('public.shoppingcart_id_seq', 30, true);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: sales sales_pkey; Type: CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_pkey PRIMARY KEY (id);


--
-- Name: shoppingcart shoppingcart_pkey; Type: CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.shoppingcart
    ADD CONSTRAINT shoppingcart_pkey PRIMARY KEY (id);


--
-- Name: sales fk_cart; Type: FK CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT fk_cart FOREIGN KEY (cartitemid) REFERENCES public.shoppingcart(id);


--
-- Name: shoppingcart fk_client; Type: FK CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.shoppingcart
    ADD CONSTRAINT fk_client FOREIGN KEY (buyer) REFERENCES public.client(id);


--
-- Name: sales fk_client; Type: FK CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT fk_client FOREIGN KEY (client) REFERENCES public.client(id);


--
-- Name: shoppingcart fk_product; Type: FK CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.shoppingcart
    ADD CONSTRAINT fk_product FOREIGN KEY (product) REFERENCES public.products(id);


--
-- Name: sales fk_products; Type: FK CONSTRAINT; Schema: public; Owner: reactiveuser
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT fk_products FOREIGN KEY (product) REFERENCES public.products(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO reactiveuser;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO reactiveuser;


--
-- PostgreSQL database dump complete
--

