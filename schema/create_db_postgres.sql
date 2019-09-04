-------------------- user --------------------
CREATE TABLE public."user"
(
	uuid uuid NOT NULL,
    username varchar(255) COLLATE pg_catalog."default" NOT NULL,
    password varchar(255) COLLATE pg_catalog."default" NOT NULL,
    avatar_url varchar(255) COLLATE pg_catalog."default",
	date_created timestamp NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (uuid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to postgres;

-------------------- conversation --------------------
CREATE TABLE public."conversation"
(
	uuid uuid NOT NULL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    status varchar(20) NOT NULL DEFAULT 'ACTIVE',
    type varchar(20) NOT NULL DEFAULT 'PRIVATE',
    date_created timestamp NOT NULL,
    CONSTRAINT conversation_pkey PRIMARY KEY (uuid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."conversation"
    OWNER to postgres;

-------------------- user conversation --------------------
CREATE TABLE public."user_conversation"
(
	uuid uuid NOT NULL,
    conversation_uuid uuid NOT NULL,
    user_uuid uuid NOT NULL,
    date_created timestamp NOT NULL,
    CONSTRAINT user_conversation_pkey PRIMARY KEY (uuid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user_conversation"
    OWNER to postgres;

-------------------- message --------------------
CREATE TABLE public."message"
(
	uuid uuid NOT NULL,
    conversation_uuid uuid NOT NULL,
    user_uuid uuid NOT NULL,
    type varchar(20) NOT NULL DEFAULT 'TEXT',
    body text NOT NULL,
    date_created timestamp NOT NULL,
    CONSTRAINT message_pkey PRIMARY KEY (uuid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."message"
    OWNER to postgres;