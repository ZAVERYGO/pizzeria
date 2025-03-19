--changeset author:id1
CREATE SCHEMA app
    AUTHORIZATION order_app;

--changeset author:id2
CREATE TABLE app.order
(
    uuid    uuid,
    sum    integer NOT NULL,
    status    character varying NOT NULL,
    user_uuid    uuid ,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT product_uuid_pk PRIMARY KEY (uuid)
);

--changeset author:id3
CREATE TABLE app.order_item
(
    uuid    uuid,
    product_uuid    uuid,
    order_uuid  uuid,
    number integer NOT NULL,
    FOREIGN KEY (order_uuid) REFERENCES app.order (uuid) ON DELETE SET NULL,
    CONSTRAINT order_item_uuid_pk PRIMARY KEY (uuid)
);

--changeset author:id4
ALTER TABLE IF EXISTS app.order
    OWNER to order_app;



