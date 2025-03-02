--changeset author:id1
CREATE SCHEMA app
    AUTHORIZATION message_app;

--changeset author:id2
CREATE TABLE app.message
(
    uuid uuid,
    email_to character varying NOT NULL,
    subject character varying NOT NULL,
    text character varying NOT NULL,
    status character varying NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT message_uuid_pk PRIMARY KEY (uuid)
);

--changeset author:id3
ALTER TABLE IF EXISTS app.message
    OWNER to message_app;




