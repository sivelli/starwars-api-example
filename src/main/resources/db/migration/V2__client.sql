create table client (
	client_id SERIAL NOT NULL,
	cpf	varchar,
	email varchar,
	name varchar,
	birth date,
	created_at timestamp,
	updated_at timestamp,
	CONSTRAINT client_key PRIMARY KEY (client_id)
);
