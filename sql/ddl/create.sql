CREATE TYPE clearance_level as enum ('0', '1', '2', '3', '4', '5', '6');

CREATE TYPE classification as enum ('A', 'B', 'C', 'D', 'E');

CREATE TYPE object_class as enum ('Безопасный', 'Кетер', 'Евклид', 'Таумиэль',
    'Аполлион', 'Архонт', 'Нейтрализованный', 'Обоснованный', 'Неприменимо');

CREATE TYPE trigger_type as enum ('Инъекция', 'Внепланово');

CREATE TYPE log_status as enum ('В ПОДГОТОВКЕ', 'ДЛЯ ОГРАНИЧЕННОГО ПОЛЬЗОВАНИЯ');

CREATE TABLE personnel
(
    id              serial PRIMARY KEY,
    name            varchar(60),
    surname         varchar(60),
    clearance_level clearance_level,
    classification  classification
);

CREATE TABLE access_key
(
    ssh_key      varchar PRIMARY KEY,
    personnel_id int REFERENCES personnel (id) on delete cascade on update cascade
);

CREATE TABLE mobile_group
(
    id      serial PRIMARY KEY,
    name    varchar(60),
    created timestamp
);

CREATE TABLE mobile_group_members
(
    mobile_group_id int REFERENCES mobile_group (id) on delete restrict on update cascade,
    personnel_id    int REFERENCES personnel (id) on delete restrict on update cascade,
    PRIMARY KEY (mobile_group_id, personnel_id)
);

CREATE table location
(
    id        serial PRIMARY KEY,
    latitude  decimal(9, 6)
        CONSTRAINT earth_latitude CHECK ( latitude >= -90 and latitude <= 90 ),
    longitude decimal(9, 6)
        CONSTRAINT earth_longitude CHECK ( longitude >= -180 and longitude <= 180 )
);

CREATE TABLE retrieval
(
    id                   serial PRIMARY KEY,
    location_id          int REFERENCES location (id) on delete restrict on update cascade,
    mobile_group_id      int REFERENCES mobile_group (id) on delete restrict on update cascade,
    return_to_reality    timestamp
        CONSTRAINT time_of_returns CHECK (return_to_reality <= return_to_foundation),
    return_to_foundation timestamp NULL,
    succeed              bool
);

CREATE TABLE foundation
(
    id          serial PRIMARY KEY,
    location_id int REFERENCES location (id) on delete restrict on update cascade
);

CREATE TABLE scp_object
(
    id            int PRIMARY KEY,
    name          varchar(80),
    description   text,
    object_class  object_class DEFAULT 'Неприменимо',
    foundation_id int NULL REFERENCES foundation (id) on delete set null on update cascade
);

CREATE TABLE equipment
(
    id   serial PRIMARY KEY,
    name varchar(80)
);

CREATE TABLE item
(
    id   serial PRIMARY KEY,
    name varchar(80)
);

CREATE TABLE equipment_contents
(
    equipment_id int REFERENCES equipment (id) on delete restrict on update cascade,
    item_id      int REFERENCES item (id) on delete restrict on update cascade,
    PRIMARY KEY (equipment_id, item_id)
);

CREATE TABLE priming
(
    id            serial PRIMARY KEY,
    scp_object_id int NULL
        CONSTRAINT "personnel_id_undefined" CHECK ( (scp_object_id IS NOT NULL and personnel_id IS NOT NULL) or
                                                    (scp_object_id IS NULL) ),
    personnel_id  int NULL REFERENCES personnel (id) on delete restrict on update cascade,
    FOREIGN KEY (scp_object_id) REFERENCES scp_object (id) on delete restrict on update cascade
);

CREATE TABLE excursion_log
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

CREATE TABLE excursion_contents
(
    excursion_log_id int REFERENCES excursion_log (id) on delete restrict on update cascade,
    item_id          int REFERENCES item (id) on delete restrict on update cascade,
    PRIMARY KEY (excursion_log_id, item_id)
);