INSERT INTO customers (name, address, lon, lat)
VALUES ('Белов', 'Казань, Ямашева 52', 49.124776, 55.828106),
       ('Воробей', 'Казань, Мусина 54', 49.124677, 55.832839),
       ('Городец', 'Казань, Бутлерова 39', 49.134307, 55.789006),
       ('Давыдов', 'Казань, Профсоюзная 15', 49.116403, 55.791511),
       ('Матвеев', 'Казань, Чуйкова 63', 49.140298, 55.836777);


INSERT INTO vendors (name, address, lon, lat)
VALUES ('Cafe La Vita', 'Казань, Чистопольская 19А', 49.114795, 55.81997),
       ('Агафредо', 'Казань, Галактионова 6', 49.12633, 55.792706),
       ('Утка в котелке', 'Казань, Дзержинского 13', 49.12633, 55.792706),
       ('Fusion of Asia', 'Казань, Лево-Булачная 24/1', 49.107366, 55.791653);

INSERT INTO deliverers (name, location, lon, lat)
VALUES ('Смелов', 'Казань, Абсалямова 37', 49.109549, 55.824673),
       ('Жеглов', 'Казань, Амирхана 5', 49.131288, 55.821254),
       ('Багдарян', 'Казань, Баумана 68', 49.131288, 55.821254),
       ('Сванидзе', 'Казань, Коротченко 2', 49.097835, 55.79387),
       ('Рамирес', 'Казань, Горького 15', 49.131091, 55.794097),
       ('Броневой', 'Казань, Гривская 45', 49.093352, 55.80826),
       ('Ткач', 'Казань, Сулеймановой 7', 49.088313, 55.81298);

INSERT INTO categories (name, delivery_coefficient)
VALUES ('food', 5);

INSERT INTO products (name, category_id, image_url, unit_price, vendor_id)
VALUES ('pizza', 1, 'url', 500, 1),
       ('pasta', 1, 'url', 300, 1),
       ('pizza', 1, 'url', 600, 2),
       ('pasta', 1, 'url', 400, 2),
       ('утка', 1, 'url', 850, 3),
       ('оливье', 1, 'url', 550, 3),
       ('том ям', 1, 'url', 490, 4),
       ('курица терияки', 1, 'url', 420, 4);

INSERT INTO carts (customer_id)
VALUES (1),
       (2),
       (2),
       (3),
       (4),
       (4);

INSERT INTO carts_products (cart_id, product_id, qty)
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 3, 2),
       (2, 4, 1),
       (3, 5, 1),
       (4, 7, 2),
       (5, 5, 1),
       (5, 6, 2),
       (6, 7, 1);

INSERT INTO orders (order_id, cart_id, deliverer_id, date_time, delivery_price, status)
VALUES (1, 1, 1, '2020-12-10T09:12:14.395+00:00', 219, 0),
       (2, 2, 5, '2020-12-11T14:10:14.395+00:00', 893, 0),
       (3, 3, 5, '2020-12-11T12:25:14.395+00:00', 893, 0),
       (4, 4, 4, '2020-12-12T18:45:14.395+00:00', 342, 0);


