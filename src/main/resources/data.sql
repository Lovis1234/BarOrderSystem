SET foreign_key_checks = 0;
INSERT INTO users (username, password) VALUES ('Stan', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa'),
                                                              ('Pieter', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa'),
                                                              ('Piebe', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa');
INSERT INTO barkeepers (id, name) VALUES (1001,'Maarten'),
                                         (1002,'Baian');

INSERT INTO customer (id,name) VALUES (2001,'Piebe'),
                                        (2002,'Yannick');

INSERT INTO authorities (id, username, authority) VALUES (3001, 'Pieter', 'ROLE_STAFF'),
                                                         (3002, 'Piebe', 'ROLE_CUSTOMER'),
                                                         (3003, 'Pieter', 'ROLE_CUSTOMER');

INSERT INTO ingredients (id, name, price)
VALUES (7001, 'Wodka', 3.00),
    (7002, 'Rum', 3.00),
    (7003, 'Gin', 3.00),
    (7004, 'Whiskey', 3.00),
    (7005, 'Tequila', 3.00),
    (7006, 'Berenburg', 1.50),
    (7007, 'Coke', 2),
    (7008, 'Orange', 2),
    (7009, 'Sprite', 2),
    (7010, 'Cassis', 2),
    (7011, 'Tonic', 2),
    (7012, 'Fristi',3.00),
    (7013, 'Water',1.5),
    (7014, 'Triple Sec',1.5),
    (7015, 'Lemon Juice',0.8),
    (7016, 'Ice',0.2);

INSERT INTO drinks (id, name,picture_id,permanent,price) VALUES (8001,'Berenburg Cola',2,true,3.7),
                                                                (8002,'Long Island Ice Tea',null,true,14.5),
                                                                (8003,'Bacardi 7UP',null,true,5.2);


INSERT INTO drinks_ingredients (drink_id, ingredients_id) VALUES (8001,7006),
                                                                 (8001,7007),
                                                                 (8001,7016),
                                                                 (8002,7001),
                                                                 (8002,7002),
                                                                 (8002,7003),
                                                                 (8002,7005),
                                                                 (8002,7014),
                                                                 (8002,7015),
                                                                 (8002,7016),
                                                                 (8003,7002),
                                                                 (8003,7009),
                                                                 (8003,7016);

INSERT INTO customer_invoices VALUES (2001,1);
INSERT INTO order_line (id, price, status, barkeeper_id, customer_id)  VALUES (9001,0,0,null,2001),
                                                                              (9002,0,0,null,2001);





