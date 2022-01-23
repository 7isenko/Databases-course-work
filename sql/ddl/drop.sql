drop table if exists access_key;

drop table if exists mobile_group_members;

drop table if exists equipment_contents;

drop table if exists excursion_contents;

drop table if exists item;

drop table if exists excursion_log;

drop table if exists retrieval;

drop table if exists mobile_group;

drop table if exists equipment;

drop table if exists priming;

drop table if exists personnel;

drop table if exists scp_object;

drop table if exists foundation;

drop table if exists location;

drop function go_on_excursion(scp_object_id integer, personnel_id integer, equipment_id integer);

DROP FUNCTION make_reports();

drop type if exists clearance_level;

drop type if exists classification;

drop type if exists object_class;

drop type if exists trigger_type;

drop type if exists log_status;
