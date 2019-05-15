CREATE SCHEMA IF NOT EXISTS admin_web_console;

SET SCHEMA 'admin_web_console';

CREATE TABLE customer (
    id          serial  PRIMARY KEY,
    customer_id   int not null unique,
    processing_settings_id int references processing_settings(id),
    short_name        varchar(128) NOT NULL,
    display_name   varchar(128) NOT NULL,
    inn         varchar(10) NOT NULL CHECK(length(inn) = 10),
    enabled     bool,
    version     int NOT NULL default 0
);

CREATE UNIQUE INDEX ON customer(customer_id);

create table processing_settings (
	id serial primary key,
	allowed_delivery_type varchar(50),
);