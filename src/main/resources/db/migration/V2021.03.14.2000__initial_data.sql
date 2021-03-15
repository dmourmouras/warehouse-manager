-- Initial Data for dev and test purposes

INSERT INTO product (name, price) VALUES ('Dining Chair', 60.0);
INSERT INTO product (name, price) VALUES ('Dinning Table', 199.99);

INSERT INTO article (name, stock) VALUES ('leg', 12);
INSERT INTO article (name, stock) VALUES ('screw', 17);
INSERT INTO article (name, stock) VALUES ('seat', 2);
INSERT INTO article (name, stock) VALUES ('table top', 1);

INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (1, 1, 4);
INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (1, 2, 8);
INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (1, 3, 1);
INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (2, 1, 4);
INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (2, 2, 8);
INSERT INTO product_article (product_id, article_id, article_amount_needed) VALUES (2, 4, 1);
