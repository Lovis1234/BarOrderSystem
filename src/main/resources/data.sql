INSERT INTO users (username, password, enabled, email) VALUES ('Stan', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa', true, 'test@testing.tst'),
                                                              ('Pieter', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa', true, 'test@testing.tst'),
                                                              ('Piebe', '$2a$12$aoyOCxY0e1LmiTSAV4hQtelwLwLURBdeSLGH1oibivgSslWjI0dSa', true, 'test@testing.tst');

INSERT INTO customer (id,name) VALUES (6911,'Piebe');

INSERT INTO authorities (id, username, authority) VALUES (3001, 'Stan', 'ROLE_Owner'),
                                                         (3002, 'Pieter', 'ROLE_BarEmployee'),
                                                         (3003, 'Piebe', 'ROLE_Customer');
INSERT INTO ingredients (id, name, price)
VALUES (7001, 'Wodka', 3.00),
    (7002, 'Rum', 3.00),
    (7003, 'Gin', 3.00),
    (7004, 'Whisky', 5.00),
    (7005, 'Tequila', 5.00),
    (7006, 'Berenburg', 1.50),
    (7007, 'Cola', 2),
    (7008, 'Sinas', 2),
    (7009, 'Sprite', 2),
    (7010, 'Cassis', 2),
    (7011, 'Tonic', 2),
    (7012, 'Fristi',3.00),
    (7013, 'Water',2),
    (7014, 'Ijs',0.5);

INSERT INTO drinks (id, name) VALUES (8001,'Bacardi Cola');

INSERT INTO drinks_ingredients (drink_id, ingredients_id) VALUES (8001,7002),
                                                                 (8001,7007);




