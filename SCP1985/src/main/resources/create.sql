DO
$$
    BEGIN
        CREATE TYPE clearance_level as enum ('0', '1', '2', '3', '4', '5', '6');
    EXCEPTION
        WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE 'type clearance_level already exists. it will be skipped';
    END
$$;

DO
$$
    BEGIN
        CREATE TYPE classification as enum ('A', 'B', 'C', 'D', 'E');
    EXCEPTION
        WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE 'type classification already exists. it will be skipped';
    END
$$;
DO
$$
    BEGIN
        CREATE TYPE object_class as enum ('Безопасный', 'Кетер', 'Евклид', 'Таумиэль',
            'Аполлион', 'Архонт', 'Нейтрализованный', 'Обоснованный', 'Неприменимо');
    EXCEPTION
        WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE 'type object_class already exists. it will be skipped';
    END
$$;
DO
$$
    BEGIN
        CREATE TYPE trigger_type as enum ('Инъекция', 'Внепланово');
    EXCEPTION
        WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE 'type trigger_type already exists. it will be skipped';
    END
$$;
DO
$$
    BEGIN
        CREATE TYPE log_status as enum ('В_ПОДГОТОВКЕ', 'ДЛЯ_ОГРАНИЧЕННОГО_ПОЛЬЗОВАНИЯ');
    EXCEPTION
        WHEN DUPLICATE_OBJECT THEN
            RAISE NOTICE 'type log_status already exists. it will be skipped';
    END
$$;

CREATE TABLE IF NOT EXISTS personnel
(
    id              serial PRIMARY KEY,
    name            varchar(60),
    surname         varchar(60),
    clearance_level clearance_level,
    classification  classification
);

CREATE TABLE IF NOT EXISTS access_key
(
    ssh_key      varchar PRIMARY KEY,
    personnel_id int REFERENCES personnel (id) on delete cascade on update cascade
);

CREATE TABLE IF NOT EXISTS mobile_group
(
    id      serial PRIMARY KEY,
    name    varchar(60),
    created timestamp
);

CREATE TABLE IF NOT EXISTS mobile_group_members
(
    mobile_group_id int REFERENCES mobile_group (id) on delete restrict on update cascade,
    personnel_id    int REFERENCES personnel (id) on delete restrict on update cascade,
    PRIMARY KEY (mobile_group_id, personnel_id)
);

CREATE table IF NOT EXISTS location
(
    id        serial PRIMARY KEY,
    latitude  decimal(9, 6)
        CONSTRAINT earth_latitude CHECK ( latitude >= -90 and latitude <= 90 ),
    longitude decimal(9, 6)
        CONSTRAINT earth_longitude CHECK ( longitude >= -180 and longitude <= 180 )
);

CREATE TABLE IF NOT EXISTS retrieval
(
    id                   serial PRIMARY KEY,
    location_id          int REFERENCES location (id) on delete restrict on update cascade,
    mobile_group_id      int REFERENCES mobile_group (id) on delete restrict on update cascade,
    return_to_reality    timestamp
        CONSTRAINT time_of_returns CHECK (return_to_reality <= return_to_foundation),
    return_to_foundation timestamp NULL,
    succeed              bool
);

CREATE TABLE IF NOT EXISTS foundation
(
    id          serial PRIMARY KEY,
    location_id int REFERENCES location (id) on delete restrict on update cascade
);

CREATE TABLE IF NOT EXISTS scp_object
(
    id            int PRIMARY KEY,
    name          varchar(80),
    description   text,
    object_class  object_class DEFAULT 'Неприменимо',
    foundation_id int NULL REFERENCES foundation (id) on delete set null on update cascade
);

CREATE TABLE IF NOT EXISTS equipment
(
    id   serial PRIMARY KEY,
    name varchar(80)
);

CREATE TABLE IF NOT EXISTS item
(
    id   serial PRIMARY KEY,
    name varchar(80)
);

CREATE TABLE IF NOT EXISTS equipment_contents
(
    equipment_id int REFERENCES equipment (id) on delete restrict on update cascade,
    item_id      int REFERENCES item (id) on delete restrict on update cascade,
    PRIMARY KEY (equipment_id, item_id)
);

CREATE TABLE IF NOT EXISTS priming
(
    id            serial PRIMARY KEY,
    scp_object_id int NULL REFERENCES scp_object (id) on delete restrict on update cascade
        CONSTRAINT "personnel_id_undefined" CHECK ( (scp_object_id IS NOT NULL and personnel_id IS NOT NULL) or
                                                    (scp_object_id IS NULL) ),
    personnel_id  int NULL REFERENCES personnel (id) on delete restrict on update cascade
);

CREATE TABLE IF NOT EXISTS excursion_log
(
    id                  serial PRIMARY KEY,
    trigger_type        trigger_type,
    trigger_committed   timestamp,
    equipment_id        int REFERENCES equipment (id) on delete restrict on update cascade,
    reality_description text,
    log_status          log_status,
    retrieval_id        int REFERENCES retrieval (id) on delete restrict on update cascade,
    note                text,
    priming_id          int REFERENCES priming (id) on delete restrict on update cascade
);

CREATE TABLE IF NOT EXISTS excursion_contents
(
    excursion_log_id int REFERENCES excursion_log (id) on delete restrict on update cascade,
    item_id          int REFERENCES item (id) on delete restrict on update cascade,
    PRIMARY KEY (excursion_log_id, item_id)
);

CREATE OR REPLACE FUNCTION check_level() RETURNS TRIGGER AS
$$
DECLARE
    level clearance_level;
BEGIN
    level = (SELECT clearance_level
             FROM personnel
             WHERE NEW.personnel_id = personnel.id);
    IF (level = '4' or level = '5') THEN
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS priming_personnel_level on priming;
CREATE TRIGGER priming_personnel_level
    BEFORE INSERT OR UPDATE
    on priming
    FOR EACH ROW
    WHEN ( NEW.personnel_id is NOT NULL and NEW.scp_object_id is NOT NULL)
execute procedure check_level();

CREATE OR REPLACE FUNCTION get_retrieval_location()
    RETURNS TABLE
            (
                latitude  decimal(9, 6),
                longitude decimal(9, 6)
            )
AS
$$
DECLARE
    latitude  decimal(9, 6);
    longitude decimal(9, 6);
BEGIN
    latitude = random() * 180 - 90;
    longitude = random() * 360 - 180;
    RETURN (latitude, longitude);
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_priming(scp_object_id_param integer, personnel_id_param integer) RETURNS integer AS
$$
BEGIN
    RETURN (SELECT priming.id
            from priming
                     LEFT JOIN excursion_log el on priming.id = el.priming_id
            WHERE priming.scp_object_id = scp_object_id_param
              and priming.personnel_id = personnel_id_param
              and el.id is NULL
            LIMIT 1);
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION go_on_excursion(scp_object_id integer, personnel_id integer, equipment_id integer)
    RETURNS integer AS
$$
DECLARE
    probability_of_right_priming decimal(3, 2);
    probe                        decimal(3, 2);
    right_priming                bool;
    id_location                  integer;
    id_retrieval                 integer;
    id_priming                   integer;
    id_item                      integer;
    id_excursion                 integer;
    last_location                TABLE
                                 (
                                     latitude  decimal(9, 6),
                                     longitude decimal(9, 6)
                                 );
    latitude                     decimal(9, 6);
    longitude                    decimal(9, 6);
BEGIN

    probability_of_right_priming = 0.05;
    IF (personnel_id is NOT NULL) THEN
        probability_of_right_priming = 0.70;
        IF (scp_object_id is NOT NULL) THEN
            probability_of_right_priming = 0.95;
        end if;
    end if;

    probe = random();
    right_priming = false;
    IF (probe <= probability_of_right_priming) THEN
        right_priming = true;
    end if;

    last_location = (SELECT * from get_retrieval_location());
    latitude = last_location.latitude;
    longitude = last_location.longitude;
    INSERT INTO location (latitude, longitude) VALUES (latitude, longitude);

    id_location = (SELECT id
                   from location
                   WHERE location.latitude = latitude
                     AND location.longitude = longitude
                   LIMIT 1);
    id_priming = (SELECT priming.id
                  from priming
                           LEFT JOIN excursion_log el on priming.id = el.priming_id
                  WHERE el.id is NULL
                  LIMIT 1);

    IF (id_priming IS NULL) THEN
        RETURN 0;
    end if;

    IF (NOT right_priming and equipment_id IS NULL) THEN

        INSERT INTO retrieval (location_id, mobile_group_id, return_to_reality, return_to_foundation, succeed)
        VALUES (id_location, 1, localtimestamp(0), NULL, FALSE);

        id_retrieval = (SELECT id from retrieval ORDER BY id DESC LIMIT 1);
        INSERT INTO excursion_log (trigger_type, trigger_committed,
                                   equipment_id, reality_description, log_status,
                                   retrieval_id, note, priming_id)
        VALUES ('Внепланово', localtimestamp(0), equipment_id, 'blablabla', 'В_ПОДГОТОВКЕ',
                id_retrieval, 'No notes', id_priming);

        RETURN 0;


    ELSEIF (NOT right_priming) THEN

        INSERT INTO retrieval (location_id, mobile_group_id, return_to_reality, return_to_foundation, succeed)
        VALUES (id_location, 1, localtimestamp(0), NULL, FALSE);

        id_retrieval = (SELECT id from retrieval ORDER BY id DESC LIMIT 1);
        INSERT INTO excursion_log (trigger_type, trigger_committed,
                                   equipment_id, reality_description, log_status,
                                   retrieval_id, note, priming_id)
        VALUES ('Внепланово', localtimestamp(0), equipment_id, 'alalalal', 'ДЛЯ_ОГРАНИЧЕННОГО_ПОЛЬЗОВАНИЯ',
                id_retrieval, 'No notes', id_priming);

        RETURN 0;

    ELSE

        INSERT INTO retrieval (location_id, mobile_group_id, return_to_reality, return_to_foundation, succeed)
        VALUES (id_location, 1, localtimestamp(0), NULL, FALSE);

        INSERT INTO item (name) VALUES ('some word');
        id_item = (SELECT id from item ORDER BY id DESC LIMIT 1);


        id_retrieval = (SELECT id from retrieval ORDER BY id DESC LIMIT 1);
        INSERT INTO excursion_log (trigger_type, trigger_committed,
                                   equipment_id, reality_description, log_status,
                                   retrieval_id, note, priming_id)
        VALUES ('Инъекция', localtimestamp(0), equipment_id, 'hahahaha', 'ДЛЯ_ОГРАНИЧЕННОГО_ПОЛЬЗОВАНИЯ',
                id_retrieval, 'No notes', id_priming);

        id_excursion = (SELECT id from excursion_log ORDER BY id DESC LIMIT 1);

        INSERT INTO excursion_contents (excursion_log_id, item_id) VALUES (id_excursion, id_item);

        RETURN 1;
    end if;
end
$$ LANGUAGE plpgsql;
