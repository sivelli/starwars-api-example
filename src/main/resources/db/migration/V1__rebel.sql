create table rebel (
	rebel_id SERIAL NOT NULL,
	name varchar,
	gender varchar,
	birth date,
	created_at timestamp,
	galaxy_latitude double precision,
	galaxy_longitude double precision,
	galaxy_name varchar,
	location_updated_at timestamp,
	traitor_reports int,
	inventory json,
	CONSTRAINT rebel_key PRIMARY KEY (rebel_id)
);

create table betrayal_indication (
	rebel_reporter INTEGER NOT NULL,
	rebel_reported INTEGER NOT NULL,
	reported_at timestamp NOT NULL,
	CONSTRAINT betrayal_indication_key PRIMARY KEY (rebel_reporter, rebel_reported)
);