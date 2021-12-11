CREATE TYPE clearance_level as enum('1', '2', '3', '4', '5');

CREATE TYPE classification as enum ('A', 'B', 'C', 'D', 'E', 'F', 'G', 'K');

CREATE TYPE object_class as enum ('euclid', 'keter', 'safe');

CREATE TYPE trigger_type as enum ('injection', 'kill');

CREATE TYPE log_status as enum ('in process', 'done', 'limited access');

CREATE TABLE personnel (
                           id serial PRIMARY KEY,
                           name varchar(40),
                           surname varchar(40),
                           clearance_level clearance_level,
                           classification classification
);

CREATE TABLE access_key (
                            ssh_key varchar PRIMARY KEY,
                            personnel_id int REFERENCES personnel(id) on delete cascade on update cascade
);

CREATE TABLE mobile_group (
                              id serial PRIMARY KEY,
                              name varchar(40),
                              created timestamp
);

CREATE TABLE mobile_group_members (
                                      mobile_group_id int REFERENCES mobile_group(id) on delete restrict on update cascade,
                                      personnel_id int REFERENCES personnel(id) on delete restrict on update cascade,
                                      PRIMARY KEY (mobile_group_id, personnel_id)
);

CREATE table location (
                          id serial PRIMARY KEY,
                          latitude decimal(10, 8) CONSTRAINT earth_latitude CHECK ( latitude >= -90 and latitude <= 90 ),
                          longitude decimal(10, 8) CONSTRAINT earth_longitude CHECK ( longitude >= -90 and longitude <= 90 )
);

CREATE TABLE retrieval (
                           id serial PRIMARY KEY,
                           location_id int REFERENCES location(id) on delete restrict on update cascade,
                           mobile_group_id int REFERENCES mobile_group(id) on delete restrict on update cascade,
                           return_to_reality timestamp CONSTRAINT time_of_returns CHECK (return_to_reality <= return_to_foundation),
                           return_to_foundation timestamp NULL,
                           succeed bool
);

CREATE TABLE foundation (
                            id serial PRIMARY KEY,
                            location_id int REFERENCES location(id) on delete restrict on update cascade
);

CREATE TABLE "scp-object" (
                              id serial,
                              tag smallint,
                              country_key varchar(2) NULL,
                              name varchar(40),
                              object_class object_class,
                              foundation_id int NULL REFERENCES foundation(id) on delete set null on update cascade,
                              PRIMARY KEY (id, tag)
);

CREATE TABLE equipment (
                           id serial PRIMARY KEY,
                           name varchar(40)
);

CREATE TABLE item (
                      id serial PRIMARY KEY,
                      name varchar(40)
);

CREATE TABLE equipment_contents (
                                    equipment_id int REFERENCES equipment(id) on delete restrict on update cascade,
                                    item_id int REFERENCES item(id) on delete restrict on update cascade,
                                    PRIMARY KEY (equipment_id, item_id)
);

CREATE TABLE priming (
                         id serial PRIMARY KEY,
                         "scp-object_id" int NULL CONSTRAINT "personnel_id_undefined" CHECK ( ("scp-object_id" IS NOT NULL and personnel_id IS NOT NULL) or ("scp-object_id" IS NULL) ),
                         "scp-object_tag" smallint NULL,
                         personnel_id int NULL REFERENCES personnel(id) on delete restrict on update cascade,
                         FOREIGN KEY ("scp-object_id", "scp-object_tag") REFERENCES "scp-object"(id, tag) on delete restrict on update cascade
);

CREATE TABLE excursion_log (
                               id serial PRIMARY KEY,
                               trigger_type trigger_type,
                               trigger_commited timestamp,
                               equipment_id int REFERENCES equipment(id) on delete restrict on update cascade,
                               reality_description varchar(1000),
                               log_status log_status,
                               retrieval_id int REFERENCES retrieval(id) on delete restrict on update cascade,
                               required_extra_research bool,
                               note varchar(100),
                               priming_id int REFERENCES priming(id) on delete restrict on update cascade
);

CREATE TABLE excursion_contents (
                                    excursion_log_id int REFERENCES excursion_log(id) on delete restrict on update cascade,
                                    item_id int REFERENCES item(id) on delete restrict on update cascade,
                                    PRIMARY KEY (excursion_log_id, item_id)
);


