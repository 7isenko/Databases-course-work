truncate table access_key restart identity;

truncate table mobile_group_members restart identity;

truncate table equipment_contents restart identity;

truncate table excursion_contents restart identity;

truncate table item restart identity cascade;

truncate table excursion_log restart identity cascade;

truncate table retrieval restart identity cascade;

truncate table mobile_group restart identity cascade;

truncate table equipment restart identity cascade;

truncate table priming restart identity cascade;

truncate table personnel restart identity cascade;

truncate table scp_object restart identity cascade;

truncate table foundation restart identity cascade;

truncate table location restart identity cascade;