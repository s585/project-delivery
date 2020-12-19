CREATE TABLE customers
(
    customer_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        TEXT NOT NULL,
    address     TEXT,
    lon         DOUBLE,
    lat         DOUBLE
);

CREATE TABLE vendors
(
    vendor_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name      TEXT NOT NULL,
    address   TEXT,
    lon       DOUBLE,
    lat       DOUBLE
);

CREATE TABLE deliverers
(
    deliverer_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name         TEXT NOT NULL,
    location     TEXT NOT NULL,
    lon          DOUBLE,
    lat          DOUBLE
);

CREATE TABLE categories
(
    category_id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name                 TEXT    NOT NULL,
    delivery_coefficient INTEGER NOT NULL
);

CREATE TABLE products
(
    product_id  INTEGER PRIMARY KEY AUTO_INCREMENT,
    category_id INTEGER REFERENCES categories ON UPDATE CASCADE,
    name        TEXT    NOT NULL,
    image_url   TEXT,
    unit_price  INTEGER CHECK (unit_price > 0),
    vendor_id   INTEGER NOT NULL REFERENCES vendors ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE carts
(
    cart_id     INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customer_id INTEGER NOT NULL REFERENCES customers
);

CREATE TABLE carts_products
(
    cart_id    INTEGER NOT NULL REFERENCES carts ON DELETE CASCADE ON UPDATE CASCADE,
    product_id INTEGER NOT NULL REFERENCES products ON DELETE CASCADE ON UPDATE CASCADE,
    qty        INTEGER
);

CREATE TABLE orders
(
    order_id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    cart_id        INTEGER REFERENCES carts,
    deliverer_id   INTEGER REFERENCES deliverers,
    date_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    delivery_price INTEGER,
    status         INTEGER   DEFAULT 0
);

CREATE VIEW customers_orders AS
SELECT o.order_id,
       c.cart_id,
       customer_id,
       vendor_id,
       cat.category_id,
       o.deliverer_id,
       o.date_time,
       SUM(p.unit_price * qty) as total,
       o.delivery_price,
       o.status
FROM carts c
         INNER JOIN orders o on c.cart_id = o.cart_id
         INNER JOIN carts_products cp on c.cart_id = cp.cart_id
         INNER JOIN products p on p.product_id = cp.product_id
         INNER JOIN categories cat on cat.category_id = p.category_id
GROUP BY o.order_id
ORDER BY o.order_id;

CREATE VIEW total as
SELECT cp.cart_id, SUM(unit_price * cp.qty) as total, p.vendor_id, p.category_id
FROM carts
         INNER JOIN carts_products cp on carts.cart_id = cp.cart_id
         INNER JOIN products p on p.product_id = cp.product_id
GROUP BY cp.cart_id
LIMIT 50;

CREATE VIEW orders_carts as
SELECT o.order_id, cp.cart_id, cp.product_id, cp.qty, o.deliverer_id
FROM carts_products cp
         INNER JOIN orders o on cp.cart_id = o.cart_id
LIMIT 50;

CREATE VIEW customers_carts_orders_vendors as
SELECT c.customer_id, cp.cart_id, o.order_id, cp.product_id, cp.qty, v.vendor_id
FROM carts_products cp
         INNER JOIN carts c on cp.cart_id = c.cart_id
         INNER JOIN orders o on c.cart_id = o.cart_id
         INNer JOIN products p on p.product_id = cp.product_id
         INNER JOIN vendors v on v.vendor_id = p.vendor_id
LIMIT 50;

