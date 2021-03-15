CREATE SEQUENCE product_article_id_seq;

CREATE TABLE product_article (
    id                       int8    NOT NULL UNIQUE DEFAULT nextval('product_article_id_seq'),
    product_id               int4 REFERENCES product (id) ON DELETE CASCADE,
    article_id               int4 REFERENCES article (id) ON DELETE CASCADE,
    article_amount_needed    int4 DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
);

ALTER SEQUENCE product_article_id_seq
    OWNED BY product_article.id;

ALTER TABLE product_article
    ADD CONSTRAINT product_id_article_id_unique UNIQUE (product_id, article_id);
