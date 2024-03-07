CREATE DATABASE test;

CREATE TABLE public.users
(
    id         int8         NOT NULL,
    "name"     varchar(255) NOT NULL,
    username   varchar(50)  NOT NULL,
    "password" varchar(255) NOT NULL,
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.users_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;