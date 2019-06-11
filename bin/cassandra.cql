CREATE KEYSPACE epslite WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'DC1' : 3 };

use epslite;

CREATE TABLE letter(
	letter_id						varchar,
	date								int,
	customer_id 				int,
	customer_token 			varchar,
	raw_address 				varchar,
	fio 								varchar,
	org_name 						varchar,
	xml 								varchar,
  pdf_link            varchar,

PRIMARY KEY (letter_id)

);

CREATE TABLE letter_pk_date(
	letter_id						varchar,
	date								int,
	customer_id 				int,
	customer_token 			varchar,
	raw_address 				varchar,
	fio 								varchar,
	org_name 						varchar,
	xml 								varchar,
	pdf_link            varchar,

PRIMARY KEY (date, customer_id, letter_id)

);

CREATE TABLE letter_pk_customer(
	letter_id					varchar,
	date							int,
	customer_id 			int,
	customer_token 		varchar,
	raw_address 			varchar,
	fio 							varchar,
	org_name 					varchar,
	xml 							varchar,
	pdf_link          varchar,

PRIMARY KEY (customer_id, date, letter_id)

);