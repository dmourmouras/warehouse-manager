CREATE SEQUENCE product_id_seq;

CREATE TABLE product (
    id          int4 NOT NULL UNIQUE DEFAULT nextval('product_id_seq'),
    name        varchar(400) NOT NULL UNIQUE,
    price       DECIMAL(8,2) DEFAULT 0.0 NOT NULL
);

ALTER SEQUENCE product_id_seq
    OWNED BY product.id;
