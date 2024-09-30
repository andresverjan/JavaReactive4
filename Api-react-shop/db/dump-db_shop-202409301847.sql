--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-30 18:47:05

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
-- TOC entry 4908 (class 1262 OID 16398)
-- Name: db_shop; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db_shop WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE db_shop OWNER TO postgres;

\connect db_shop

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
    id integer NOT NULL,
    idproduct integer NOT NULL,
    quantity integer NOT NULL,
    idclient integer NOT NULL,
    nameclient character varying NOT NULL,
    nameproduct character varying NOT NULL,
    priceproduct real NOT NULL,
    delivery real DEFAULT 0 NOT NULL,
    totalpriceproducts real NOT NULL,
    totaldiscount real DEFAULT 0 NOT NULL,
    totaliva real NOT NULL
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16404)
-- Name: cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cart_id_seq OWNER TO postgres;

--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 216
-- Name: cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cart_id_seq OWNED BY public.cart.id;


--
-- TOC entry 217 (class 1259 OID 16405)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id integer NOT NULL,
    idperson integer NOT NULL,
    status character varying
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16408)
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.client_id_seq OWNER TO postgres;

--
-- TOC entry 4911 (class 0 OID 0)
-- Dependencies: 218
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 219 (class 1259 OID 16409)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    name character varying NOT NULL,
    age integer,
    gender character varying,
    address character varying,
    document character varying NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16414)
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.person_id_seq OWNER TO postgres;

--
-- TOC entry 4912 (class 0 OID 0)
-- Dependencies: 220
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 232 (class 1259 OID 16556)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_id_seq OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16531)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id integer DEFAULT nextval('public.product_id_seq'::regclass) NOT NULL,
    name character varying NOT NULL,
    description character varying,
    price real NOT NULL,
    iva real NOT NULL,
    ivarate integer NOT NULL,
    imageurl character varying,
    reference character varying NOT NULL,
    priceiva real NOT NULL,
    createdat date,
    updatedat date
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16592)
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    id integer NOT NULL,
    idproduct integer NOT NULL,
    quantity integer NOT NULL,
    type character varying NOT NULL,
    reference character varying NOT NULL,
    idsalesorder integer,
    idpurchaseorder integer
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16633)
-- Name: product_stock; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.product_stock AS
 SELECT s.idproduct,
    p.name AS nameproduct,
    sum(s.quantity) AS stock
   FROM (public.stock s
     JOIN public.product p ON ((s.idproduct = p.id)))
  GROUP BY s.idproduct, p.name;


ALTER VIEW public.product_stock OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16421)
-- Name: purchase_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_order (
    id integer NOT NULL,
    reference character varying NOT NULL,
    totalbase real NOT NULL,
    totalpurchase real NOT NULL,
    idsupplier integer NOT NULL,
    items integer NOT NULL,
    status character varying,
    date date NOT NULL,
    ivarate integer NOT NULL,
    totaliva real NOT NULL,
    namesupplier character varying NOT NULL
);


ALTER TABLE public.purchase_order OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16426)
-- Name: purchase_order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_order_detail (
    id integer NOT NULL,
    reference character varying NOT NULL,
    total real NOT NULL,
    iva real NOT NULL,
    quantity integer NOT NULL,
    cost real NOT NULL,
    ivarate integer NOT NULL,
    idproduct integer NOT NULL,
    totaliva real NOT NULL,
    nameproduct character varying NOT NULL,
    idpurchaseorder integer NOT NULL
);


ALTER TABLE public.purchase_order_detail OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16431)
-- Name: purchase_order_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchase_order_detail_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.purchase_order_detail_id_seq OWNER TO postgres;

--
-- TOC entry 4913 (class 0 OID 0)
-- Dependencies: 223
-- Name: purchase_order_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchase_order_detail_id_seq OWNED BY public.purchase_order_detail.id;


--
-- TOC entry 224 (class 1259 OID 16432)
-- Name: purchase_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchase_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.purchase_order_id_seq OWNER TO postgres;

--
-- TOC entry 4914 (class 0 OID 0)
-- Dependencies: 224
-- Name: purchase_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchase_order_id_seq OWNED BY public.purchase_order.id;


--
-- TOC entry 225 (class 1259 OID 16433)
-- Name: sales_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_order (
    id integer NOT NULL,
    reference character varying NOT NULL,
    totalbase real NOT NULL,
    totalsale real NOT NULL,
    idclient integer NOT NULL,
    items integer NOT NULL,
    status character varying NOT NULL,
    date date NOT NULL,
    ivarate integer NOT NULL,
    nameclient character varying NOT NULL,
    totaliva real NOT NULL,
    totaldiscount real NOT NULL
);


ALTER TABLE public.sales_order OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16438)
-- Name: sales_order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_order_detail (
    id integer NOT NULL,
    reference character varying NOT NULL,
    total real NOT NULL,
    iva real NOT NULL,
    quantity integer NOT NULL,
    price real NOT NULL,
    ivarate integer NOT NULL,
    idproduct integer NOT NULL,
    nameproduct character varying NOT NULL,
    idsalesorder integer NOT NULL,
    totaliva real NOT NULL
);


ALTER TABLE public.sales_order_detail OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16443)
-- Name: sales_order_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sales_order_detail_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sales_order_detail_id_seq OWNER TO postgres;

--
-- TOC entry 4915 (class 0 OID 0)
-- Dependencies: 227
-- Name: sales_order_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sales_order_detail_id_seq OWNED BY public.sales_order_detail.id;


--
-- TOC entry 228 (class 1259 OID 16444)
-- Name: sales_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sales_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sales_order_id_seq OWNER TO postgres;

--
-- TOC entry 4916 (class 0 OID 0)
-- Dependencies: 228
-- Name: sales_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sales_order_id_seq OWNED BY public.sales_order.id;


--
-- TOC entry 233 (class 1259 OID 16591)
-- Name: stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_id_seq
    AS integer
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_id_seq OWNER TO postgres;

--
-- TOC entry 4917 (class 0 OID 0)
-- Dependencies: 233
-- Name: stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_id_seq OWNED BY public.stock.id;


--
-- TOC entry 229 (class 1259 OID 16445)
-- Name: supplier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supplier (
    id integer NOT NULL,
    name character varying,
    document character varying
);


ALTER TABLE public.supplier OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16450)
-- Name: supplier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.supplier_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.supplier_id_seq OWNER TO postgres;

--
-- TOC entry 4918 (class 0 OID 0)
-- Dependencies: 230
-- Name: supplier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.supplier_id_seq OWNED BY public.supplier.id;


--
-- TOC entry 4683 (class 2604 OID 16451)
-- Name: cart id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart ALTER COLUMN id SET DEFAULT nextval('public.cart_id_seq'::regclass);


--
-- TOC entry 4686 (class 2604 OID 16452)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 4687 (class 2604 OID 16453)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 4688 (class 2604 OID 16455)
-- Name: purchase_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order ALTER COLUMN id SET DEFAULT nextval('public.purchase_order_id_seq'::regclass);


--
-- TOC entry 4689 (class 2604 OID 16456)
-- Name: purchase_order_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail ALTER COLUMN id SET DEFAULT nextval('public.purchase_order_detail_id_seq'::regclass);


--
-- TOC entry 4690 (class 2604 OID 16457)
-- Name: sales_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order ALTER COLUMN id SET DEFAULT nextval('public.sales_order_id_seq'::regclass);


--
-- TOC entry 4691 (class 2604 OID 16458)
-- Name: sales_order_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail ALTER COLUMN id SET DEFAULT nextval('public.sales_order_detail_id_seq'::regclass);


--
-- TOC entry 4694 (class 2604 OID 16595)
-- Name: stock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock ALTER COLUMN id SET DEFAULT nextval('public.stock_id_seq'::regclass);


--
-- TOC entry 4692 (class 2604 OID 16459)
-- Name: supplier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier ALTER COLUMN id SET DEFAULT nextval('public.supplier_id_seq'::regclass);


--
-- TOC entry 4883 (class 0 OID 16399)
-- Dependencies: 215
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4885 (class 0 OID 16405)
-- Dependencies: 217
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client VALUES (12, 1, 'Active');
INSERT INTO public.client VALUES (13, 12, 'Active');


--
-- TOC entry 4887 (class 0 OID 16409)
-- Dependencies: 219
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person VALUES (1, 'Leonardo Vivas', 30, 'Male', 'Calle 10 av 50 Conj Kaoba', '1090');
INSERT INTO public.person VALUES (12, 'Clifford Carter', 24, 'Male', '44545 Caterina Knolls', '10904');
INSERT INTO public.person VALUES (14, 'Dr. Sophie Haag', 26, 'Male', '30012 Shanahan Hill', 'BSD');
INSERT INTO public.person VALUES (15, 'Sara Kris', 31, 'Male', '01718 Harmon Estate', 'ZAR');
INSERT INTO public.person VALUES (16, 'Natalie Berge', 28, 'Male', '3500 Christiansen Drives', '898-567-7064');
INSERT INTO public.person VALUES (17, 'Lora Gleichner', 40, 'Male', '8900 Arnaldo Islands', '756-937-8813');
INSERT INTO public.person VALUES (18, 'Bernice Grimes', 20, 'Male', '69832 Abraham Prairie', '1090375');
INSERT INTO public.person VALUES (19, 'Belinda Rice', 18, 'Male', '359 Zelda Route', '1090517');
INSERT INTO public.person VALUES (20, 'Max Lang', 32, 'Male', '786 Hallie Spring', '1090123325');
INSERT INTO public.person VALUES (21, 'Willie Zboncak', 31, 'Male', '574 Weissnat Square', '1090123247');
INSERT INTO public.person VALUES (22, 'Clifton Swift', 28, 'Male', '715 Farrell Stravenue', '1090123808');
INSERT INTO public.person VALUES (23, 'Shirley Lueilwitz', 29, 'Male', '27509 Gregg Gateway', '109012390');


--
-- TOC entry 4899 (class 0 OID 16531)
-- Dependencies: 231
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product VALUES (7, 'Teclado gamer', 'Teclado gamer modelo gb800', 200000, 38000, 19, 'http://storage/image', 'gb800', 238000, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (4, 'Teclado inalambrico', 'Teclado inalambrico modelo kb200', 150000, 22500, 19, 'http://storage/imag', 'kb200', 178500, NULL, '2024-09-30');
INSERT INTO public.product VALUES (10, 'Small Wooden Bike', 'Teclado inalambrico modelo kb200', 5000, 950, 19, 'http://storage/image', 'Tuna', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (11, 'Rustic Metal Chair', 'Teclado inalambrico modelo kb200', 5000, 950, 19, 'http://storage/image', 'Bacon', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (13, 'Generic Cotton Table', 'Awesome Rubber Hat', 5000, 950, 19, 'http://storage/image', 'Mouse', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (14, 'Practical Cotton Car', 'Ergonomic Wooden Table', 5000, 950, 19, 'http://storage/image', 'Fish', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (15, 'Incredible Concrete Ball', 'Practical Soft Fish', 5000, 950, 19, 'http://placeimg.com/640/480', 'Shirt', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (17, 'Generic Wooden Sausages', 'Ergonomic Frozen Salad', 5000, 950, 19, 'http://placeimg.com/640/480', 'Chicken', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (18, 'Ergonomic Metal Fish', 'Rustic Granite Gloves', 5000, 950, 19, 'http://placeimg.com/640/480', '37*50', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (19, 'Refined Soft Keyboard', 'Unbranded Plastic Hat', 5000, 950, 19, 'http://placeimg.com/640/480', '954001', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (20, 'Generic Fresh Computer', 'Fantastic Cotton Tuna', 5000, 950, 19, 'http://placeimg.com/640/480', '838001', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (21, 'Tasty Soft Chicken', 'Rustic Frozen Tuna', 5000, 950, 19, 'http://placeimg.com/640/480', '323001', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (22, 'Sleek Fresh Chicken', 'Rustic Fresh Pizza', 5000, 950, 19, 'http://placeimg.com/640/480', 'P00746', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (23, 'Tasty Plastic Chips', 'Sleek Metal Bike', 5000, 950, 19, 'http://placeimg.com/640/480', 'P00949', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (24, 'Rustic Concrete Tuna', 'Generic Frozen Ball', 5000, 950, 19, 'http://placeimg.com/640/480', 'P00285', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (26, 'Unbranded Fresh Salad', 'Incredible Rubber Bacon', 5000, 950, 19, 'http://placeimg.com/640/480', 'P00628', 5950, '2024-09-27', '2024-09-30');
INSERT INTO public.product VALUES (25, 'Small Soft Pizza', 'Ergonomic Steel Chips', 5000, 950, 19, 'http://placeimg.com/640/480', 'P00968', 5950, '2024-09-27', '2024-09-30');


--
-- TOC entry 4889 (class 0 OID 16421)
-- Dependencies: 221
-- Data for Name: purchase_order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4890 (class 0 OID 16426)
-- Dependencies: 222
-- Data for Name: purchase_order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4893 (class 0 OID 16433)
-- Dependencies: 225
-- Data for Name: sales_order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4894 (class 0 OID 16438)
-- Dependencies: 226
-- Data for Name: sales_order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4902 (class 0 OID 16592)
-- Dependencies: 234
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4897 (class 0 OID 16445)
-- Dependencies: 229
-- Data for Name: supplier; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.supplier VALUES (1, 'Supplier medellin', '1090');
INSERT INTO public.supplier VALUES (5, 'Pete Ritchie', '50123469');
INSERT INTO public.supplier VALUES (6, 'Lena Watsica', '50123282');
INSERT INTO public.supplier VALUES (7, 'Cedric Abernathy', '50123905');
INSERT INTO public.supplier VALUES (8, 'Armando Brakus', '50123628');
INSERT INTO public.supplier VALUES (9, 'Walter Hagenes', '50123434');


--
-- TOC entry 4919 (class 0 OID 0)
-- Dependencies: 216
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cart_id_seq', 81, true);


--
-- TOC entry 4920 (class 0 OID 0)
-- Dependencies: 218
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 18, true);


--
-- TOC entry 4921 (class 0 OID 0)
-- Dependencies: 220
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 25, true);


--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 232
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 29, true);


--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 223
-- Name: purchase_order_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_order_detail_id_seq', 128, true);


--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 224
-- Name: purchase_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_order_id_seq', 122, true);


--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 227
-- Name: sales_order_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sales_order_detail_id_seq', 133, true);


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 228
-- Name: sales_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sales_order_id_seq', 151, true);


--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 233
-- Name: stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_id_seq', 111, true);


--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 230
-- Name: supplier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.supplier_id_seq', 10, true);


--
-- TOC entry 4696 (class 2606 OID 16461)
-- Name: cart cart_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pk PRIMARY KEY (id);


--
-- TOC entry 4698 (class 2606 OID 16463)
-- Name: client client_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 16520)
-- Name: client client_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_unique UNIQUE (idperson);


--
-- TOC entry 4702 (class 2606 OID 16465)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 16514)
-- Name: person person_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_unique UNIQUE (document);


--
-- TOC entry 4722 (class 2606 OID 16565)
-- Name: product product_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_unique UNIQUE (reference);


--
-- TOC entry 4724 (class 2606 OID 16539)
-- Name: product producto_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT producto_pk PRIMARY KEY (id);


--
-- TOC entry 4710 (class 2606 OID 16469)
-- Name: purchase_order_detail purchase_order_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail
    ADD CONSTRAINT purchase_order_detail_pk PRIMARY KEY (id);


--
-- TOC entry 4706 (class 2606 OID 16471)
-- Name: purchase_order purchase_order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_pk PRIMARY KEY (id);


--
-- TOC entry 4708 (class 2606 OID 16567)
-- Name: purchase_order purchase_order_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_unique UNIQUE (reference);


--
-- TOC entry 4716 (class 2606 OID 16473)
-- Name: sales_order_detail sales_order_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail
    ADD CONSTRAINT sales_order_detail_pk PRIMARY KEY (id);


--
-- TOC entry 4712 (class 2606 OID 16475)
-- Name: sales_order sales_order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order
    ADD CONSTRAINT sales_order_pk PRIMARY KEY (id);


--
-- TOC entry 4714 (class 2606 OID 16575)
-- Name: sales_order sales_order_reference_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order
    ADD CONSTRAINT sales_order_reference_unique UNIQUE (reference);


--
-- TOC entry 4726 (class 2606 OID 16597)
-- Name: stock stock_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pk PRIMARY KEY (id);


--
-- TOC entry 4718 (class 2606 OID 16477)
-- Name: supplier supplier_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_pk PRIMARY KEY (id);


--
-- TOC entry 4720 (class 2606 OID 16516)
-- Name: supplier supplier_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_unique UNIQUE (document);


--
-- TOC entry 4727 (class 2606 OID 16478)
-- Name: cart cart_client_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_client_fk FOREIGN KEY (idclient) REFERENCES public.client(id);


--
-- TOC entry 4728 (class 2606 OID 16540)
-- Name: cart cart_producto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_producto_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4729 (class 2606 OID 16488)
-- Name: client client_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_fk FOREIGN KEY (idperson) REFERENCES public.person(id);


--
-- TOC entry 4731 (class 2606 OID 16550)
-- Name: purchase_order_detail purchase_order_detail_producto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail
    ADD CONSTRAINT purchase_order_detail_producto_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4732 (class 2606 OID 16586)
-- Name: purchase_order_detail purchase_order_detail_purchase_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail
    ADD CONSTRAINT purchase_order_detail_purchase_order_fk FOREIGN KEY (idpurchaseorder) REFERENCES public.purchase_order(id) ON DELETE CASCADE;


--
-- TOC entry 4730 (class 2606 OID 16498)
-- Name: purchase_order purchase_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_fk FOREIGN KEY (idsupplier) REFERENCES public.supplier(id);


--
-- TOC entry 4734 (class 2606 OID 16545)
-- Name: sales_order_detail sales_order_detail_producto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail
    ADD CONSTRAINT sales_order_detail_producto_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4735 (class 2606 OID 16576)
-- Name: sales_order_detail sales_order_detail_sales_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail
    ADD CONSTRAINT sales_order_detail_sales_order_fk FOREIGN KEY (idsalesorder) REFERENCES public.sales_order(id) ON DELETE CASCADE;


--
-- TOC entry 4733 (class 2606 OID 16508)
-- Name: sales_order sales_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order
    ADD CONSTRAINT sales_order_fk FOREIGN KEY (idclient) REFERENCES public.client(id);


--
-- TOC entry 4736 (class 2606 OID 16605)
-- Name: stock stock_product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_product_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4737 (class 2606 OID 16649)
-- Name: stock stock_purchase_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_purchase_order_fk FOREIGN KEY (idpurchaseorder) REFERENCES public.purchase_order(id) ON DELETE CASCADE;


--
-- TOC entry 4738 (class 2606 OID 16610)
-- Name: stock stock_sales_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_sales_order_fk FOREIGN KEY (idsalesorder) REFERENCES public.sales_order(id) ON DELETE CASCADE;


-- Completed on 2024-09-30 18:47:05

--
-- PostgreSQL database dump complete
--

