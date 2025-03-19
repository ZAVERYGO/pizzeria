--changeset author:id1
CREATE SCHEMA app
    AUTHORIZATION product_app;

--changeset author:id2
CREATE TABLE app.category
(
    uuid    uuid,
    name    character varying NOT NULL UNIQUE ,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT category_uuid_pk PRIMARY KEY (uuid)
);


--changeset author:id3
CREATE TABLE app.product
(
    uuid    uuid,
    name    character varying NOT NULL UNIQUE ,
    composition    character varying NOT NULL,
    price    integer NOT NULL,
    currency    varchar(3) NOT NULL,
    category_uuid uuid  NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT product_uuid_pk PRIMARY KEY (uuid),
    FOREIGN KEY (category_uuid) REFERENCES app.category (uuid) ON DELETE SET NULL
);

--changeset author:id4
ALTER TABLE IF EXISTS app.product
    OWNER to product_app;



