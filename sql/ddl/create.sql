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

CREATE TABLE scp_object (
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

<<<<<<< Updated upstream
CREATE TABLE priming (
                         id serial PRIMARY KEY,
                         scp_object_id int NULL CONSTRAINT "personnel_id_undefined" CHECK ( (scp_object_id IS NOT NULL and personnel_id IS NOT NULL) or (scp_object_id IS NULL) ),
                         scp_object_tag smallint NULL,
                         personnel_id int NULL REFERENCES personnel(id) on delete restrict on update cascade,
                         FOREIGN KEY (scp_object_id, scp_object_tag) REFERENCES scp_object(id, tag) on delete restrict on update cascade
=======
CREATE TABLE priming
(
    id            serial PRIMARY KEY,
    scp_object_id int NULL REFERENCES scp_object (id) on delete restrict on update cascade
        CONSTRAINT "personnel_id_undefined" CHECK ( (scp_object_id IS NOT NULL and personnel_id IS NOT NULL) or
                                                    (scp_object_id IS NULL) ),
    personnel_id  int NULL REFERENCES personnel (id) on delete restrict on update cascade
>>>>>>> Stashed changes
);

CREATE TABLE excursion_log (
                               id serial PRIMARY KEY,
                               trigger_type trigger_type,
                               trigger_commited timestamp,
                               equipment_id int REFERENCES equipment(id) on delete restrict on update cascade,
                               reality_description varchar(1000),
                               log_status log_status,
                               retrieval_id int REFERENCES retrieval(id) on delete restrict on update cascade,
                               note varchar(100),
                               priming_id int REFERENCES priming(id) on delete restrict on update cascade
);

<<<<<<< Updated upstream
CREATE TABLE excursion_contents (
                                    excursion_log_id int REFERENCES excursion_log(id) on delete restrict on update cascade,
                                    item_id int REFERENCES item(id) on delete restrict on update cascade,
                                    PRIMARY KEY (excursion_log_id, item_id)
);
=======
CREATE TABLE excursion_contents
(
    excursion_log_id int REFERENCES excursion_log (id) on delete restrict on update cascade,
    item_id          int REFERENCES item (id) on delete restrict on update cascade,
    PRIMARY KEY (excursion_log_id, item_id)
);

CREATE OR REPLACE FUNCTION check_level()  RETURNS TRIGGER AS $$
DECLARE
    level clearance_level;
BEGIN
    level = (SELECT clearance_level FROM personnel
             WHERE NEW.personnel_id = personnel.id);
    IF (level = '4' or level = '5') THEN
        RETURN NEW;
    END IF;
    RETURN NULL;
end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER priming_personnel_level BEFORE INSERT OR UPDATE on priming FOR EACH ROW
    WHEN ( NEW.personnel_id is NOT NULL and NEW.scp_object_id is NOT NULL) execute procedure check_level();
>>>>>>> Stashed changes
