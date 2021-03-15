CREATE SEQUENCE article_id_seq;

CREATE TABLE article (
    id          int4 NOT NULL UNIQUE DEFAULT nextval('article_id_seq'),
    name        varchar(400) NOT NULL UNIQUE,
    stock       int8 DEFAULT 0 NOT NULL
);

ALTER SEQUENCE article_id_seq
    OWNED BY article.id;
