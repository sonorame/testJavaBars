--
-- PostgreSQL database dump
--

-- Dumped from database version 12.12
-- Dumped by pg_dump version 12.12

-- Started on 2023-05-03 11:22:40

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2824 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 1361802)
-- Name: rdogovor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rdogovor (
    date_start timestamp without time zone,
    dog_no character varying(300),
    update_time timestamp without time zone DEFAULT now()
);


ALTER TABLE public.rdogovor OWNER TO postgres;

--
-- TOC entry 2825 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN rdogovor.date_start; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.rdogovor.date_start IS 'Дата начала аренды';


--
-- TOC entry 2826 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN rdogovor.dog_no; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.rdogovor.dog_no IS 'Номер договора';


--
-- TOC entry 2827 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN rdogovor.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.rdogovor.update_time IS 'Дата и время последнего изменения записи';


--
-- TOC entry 202 (class 1259 OID 1361796)
-- Name: suser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suser (
    user_id uuid NOT NULL,
    user_login character varying(500) NOT NULL,
    user_pass character varying(64) NOT NULL
);


ALTER TABLE public.suser OWNER TO postgres;

--
-- TOC entry 2828 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN suser.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.suser.user_id IS 'Идентификатор пользователя';


--
-- TOC entry 2829 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN suser.user_login; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.suser.user_login IS 'Логин пользователя';


--
-- TOC entry 2830 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN suser.user_pass; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.suser.user_pass IS 'Пароль пользователя';


--
-- TOC entry 2818 (class 0 OID 1361802)
-- Dependencies: 203
-- Data for Name: rdogovor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rdogovor (date_start, dog_no, update_time) FROM stdin;
2020-01-01 00:00:00	123	2021-01-01 00:00:00
2022-01-01 00:00:00	321	2023-05-01 00:00:00
2021-08-14 00:00:00	228	2023-04-28 11:19:04.917889
2019-07-01 00:00:00	567	2022-09-24 00:00:00
1990-01-01 00:00:00	951	2022-12-06 00:00:00
\.


--
-- TOC entry 2817 (class 0 OID 1361796)
-- Dependencies: 202
-- Data for Name: suser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suser (user_id, user_login, user_pass) FROM stdin;
50c25f2d-bb9d-4c7e-a23f-58185782aef8	admin	111
\.


-- Completed on 2023-05-03 11:22:42

--
-- PostgreSQL database dump complete
--

