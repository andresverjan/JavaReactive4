--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-21 10:22:18

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

DROP DATABASE db_shop;
--
-- TOC entry 4928 (class 1262 OID 16430)
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
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16450)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
    id integer NOT NULL,
    idproduct integer,
    codigo character varying,
    quantity integer,
    idcliente integer
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16449)
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
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 219
-- Name: cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cart_id_seq OWNED BY public.cart.id;


--
-- TOC entry 224 (class 1259 OID 16473)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id integer NOT NULL,
    idperson integer
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16472)
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
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 223
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 218 (class 1259 OID 16439)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    name character varying,
    age integer,
    gender character varying,
    date_of_birth date,
    address character varying,
    document character varying
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16438)
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
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 217
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 216 (class 1259 OID 16432)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id integer NOT NULL,
    name character varying NOT NULL,
    price real NOT NULL,
    description character varying,
    imageurl character varying,
    stock integer,
    iva integer,
    ivarate integer
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16431)
-- Name: producto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producto_id_seq OWNER TO postgres;

--
-- TOC entry 4933 (class 0 OID 0)
-- Dependencies: 215
-- Name: producto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producto_id_seq OWNED BY public.product.id;


--
-- TOC entry 228 (class 1259 OID 16510)
-- Name: purchase_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_order (
    id integer NOT NULL,
    reference character varying,
    total real,
    iva real,
    idsupplier integer,
    items integer,
    status character varying,
    date date,
    ivarate integer
);


ALTER TABLE public.purchase_order OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16524)
-- Name: purchase_order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_order_detail (
    id integer NOT NULL,
    reference character varying,
    total real,
    iva real,
    date date,
    quantity integer,
    cost real,
    ivarate integer,
    idproduct integer
);


ALTER TABLE public.purchase_order_detail OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16523)
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
-- TOC entry 4934 (class 0 OID 0)
-- Dependencies: 229
-- Name: purchase_order_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchase_order_detail_id_seq OWNED BY public.purchase_order_detail.id;


--
-- TOC entry 227 (class 1259 OID 16509)
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
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 227
-- Name: purchase_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchase_order_id_seq OWNED BY public.purchase_order.id;


--
-- TOC entry 226 (class 1259 OID 16496)
-- Name: sales_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_order (
    id integer NOT NULL,
    reference character varying,
    total real,
    iva real,
    idclient integer,
    items integer,
    status character varying,
    date date,
    ivarate integer
);


ALTER TABLE public.sales_order OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16531)
-- Name: sales_order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_order_detail (
    id integer NOT NULL,
    reference character varying,
    total real,
    iva real,
    date date,
    quantity integer,
    price real,
    ivarate integer,
    idproduct integer
);


ALTER TABLE public.sales_order_detail OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16530)
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
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 231
-- Name: sales_order_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sales_order_detail_id_seq OWNED BY public.sales_order_detail.id;


--
-- TOC entry 225 (class 1259 OID 16495)
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
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 225
-- Name: sales_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sales_order_id_seq OWNED BY public.sales_order.id;


--
-- TOC entry 222 (class 1259 OID 16464)
-- Name: supplier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supplier (
    id integer NOT NULL,
    name character varying,
    document character varying
);


ALTER TABLE public.supplier OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16463)
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
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 221
-- Name: supplier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.supplier_id_seq OWNED BY public.supplier.id;


--
-- TOC entry 4730 (class 2604 OID 16453)
-- Name: cart id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart ALTER COLUMN id SET DEFAULT nextval('public.cart_id_seq'::regclass);


--
-- TOC entry 4732 (class 2604 OID 16476)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 4729 (class 2604 OID 16442)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 4728 (class 2604 OID 16435)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.producto_id_seq'::regclass);


--
-- TOC entry 4734 (class 2604 OID 16513)
-- Name: purchase_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order ALTER COLUMN id SET DEFAULT nextval('public.purchase_order_id_seq'::regclass);


--
-- TOC entry 4735 (class 2604 OID 16527)
-- Name: purchase_order_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail ALTER COLUMN id SET DEFAULT nextval('public.purchase_order_detail_id_seq'::regclass);


--
-- TOC entry 4733 (class 2604 OID 16499)
-- Name: sales_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order ALTER COLUMN id SET DEFAULT nextval('public.sales_order_id_seq'::regclass);


--
-- TOC entry 4736 (class 2604 OID 16534)
-- Name: sales_order_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail ALTER COLUMN id SET DEFAULT nextval('public.sales_order_detail_id_seq'::regclass);


--
-- TOC entry 4731 (class 2604 OID 16467)
-- Name: supplier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier ALTER COLUMN id SET DEFAULT nextval('public.supplier_id_seq'::regclass);


--
-- TOC entry 4910 (class 0 OID 16450)
-- Dependencies: 220
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4914 (class 0 OID 16473)
-- Dependencies: 224
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4908 (class 0 OID 16439)
-- Dependencies: 218
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4906 (class 0 OID 16432)
-- Dependencies: 216
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4918 (class 0 OID 16510)
-- Dependencies: 228
-- Data for Name: purchase_order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4920 (class 0 OID 16524)
-- Dependencies: 230
-- Data for Name: purchase_order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4916 (class 0 OID 16496)
-- Dependencies: 226
-- Data for Name: sales_order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4922 (class 0 OID 16531)
-- Dependencies: 232
-- Data for Name: sales_order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4912 (class 0 OID 16464)
-- Dependencies: 222
-- Data for Name: supplier; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 219
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cart_id_seq', 1, false);


--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 223
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 1, false);


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 217
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 1, false);


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 215
-- Name: producto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producto_id_seq', 1, false);


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 229
-- Name: purchase_order_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_order_detail_id_seq', 1, false);


--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 227
-- Name: purchase_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_order_id_seq', 1, false);


--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 231
-- Name: sales_order_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sales_order_detail_id_seq', 1, false);


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 225
-- Name: sales_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sales_order_id_seq', 1, false);


--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 221
-- Name: supplier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.supplier_id_seq', 1, false);


--
-- TOC entry 4742 (class 2606 OID 16457)
-- Name: cart cart_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pk PRIMARY KEY (id);


--
-- TOC entry 4746 (class 2606 OID 16478)
-- Name: client client_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);


--
-- TOC entry 4740 (class 2606 OID 16446)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 4738 (class 2606 OID 16448)
-- Name: product product_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pk PRIMARY KEY (id);


--
-- TOC entry 4752 (class 2606 OID 16540)
-- Name: purchase_order_detail purchase_order_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail
    ADD CONSTRAINT purchase_order_detail_pk PRIMARY KEY (id);


--
-- TOC entry 4750 (class 2606 OID 16517)
-- Name: purchase_order purchase_order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_pk PRIMARY KEY (id);


--
-- TOC entry 4754 (class 2606 OID 16538)
-- Name: sales_order_detail sales_order_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail
    ADD CONSTRAINT sales_order_detail_pk PRIMARY KEY (id);


--
-- TOC entry 4748 (class 2606 OID 16503)
-- Name: sales_order sales_order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order
    ADD CONSTRAINT sales_order_pk PRIMARY KEY (id);


--
-- TOC entry 4744 (class 2606 OID 16471)
-- Name: supplier supplier_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_pk PRIMARY KEY (id);


--
-- TOC entry 4755 (class 2606 OID 16550)
-- Name: cart cart_client_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_client_fk FOREIGN KEY (idcliente) REFERENCES public.client(id);


--
-- TOC entry 4756 (class 2606 OID 16490)
-- Name: cart cart_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4757 (class 2606 OID 16479)
-- Name: client client_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_fk FOREIGN KEY (idperson) REFERENCES public.person(id);


--
-- TOC entry 4760 (class 2606 OID 16555)
-- Name: purchase_order_detail purchase_order_detail_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order_detail
    ADD CONSTRAINT purchase_order_detail_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4759 (class 2606 OID 16518)
-- Name: purchase_order purchase_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_fk FOREIGN KEY (idsupplier) REFERENCES public.supplier(id);


--
-- TOC entry 4761 (class 2606 OID 16560)
-- Name: sales_order_detail sales_order_detail_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order_detail
    ADD CONSTRAINT sales_order_detail_fk FOREIGN KEY (idproduct) REFERENCES public.product(id);


--
-- TOC entry 4758 (class 2606 OID 16504)
-- Name: sales_order sales_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_order
    ADD CONSTRAINT sales_order_fk FOREIGN KEY (idclient) REFERENCES public.client(id);


-- Completed on 2024-09-21 10:22:18

--
-- PostgreSQL database dump complete
--

