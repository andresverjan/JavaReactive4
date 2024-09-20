CREATE TABLE api.carritos
(
    id serial,
    client_id VARCHAR(10),
    items json,
    total double precision,
    orden_compra json,
    PRIMARY KEY (id)
);
