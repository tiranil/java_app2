CREATE TABLE customers (
    id SERIAL UNIQUE,
    first_name TEXT,
    last_name TEXT,
);

INSERT INTO customers VALUES (DEFAULT, 'Denis', 'Bolonkin');
INSERT INTO customers VALUES (DEFAULT, 'Vasya', 'Pupkin');
INSERT INTO customers VALUES (DEFAULT, 'Volodya', 'Sharapov');
