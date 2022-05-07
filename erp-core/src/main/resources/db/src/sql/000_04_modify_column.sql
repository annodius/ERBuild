alter table Counteragent
    alter column id type varchar(20);

create sequence counteragent_seq;

alter table Counteragent
    alter column id set default to_char(nextval('counteragent_seq'::regclass), 'FM99999999999');

alter table Contract
    alter column id type varchar(20),
    alter column id_counteragent type varchar(20);

create sequence contract_seq;

alter table Contract
    alter column id set default to_char(nextval('contract_seq'::regclass), 'FM99999999999');

alter table KS
    alter column id type varchar(20),
    alter column id_contract type varchar(20);

create sequence ks_seq;

alter table KS
    alter column id set default to_char(nextval('ks_seq'::regclass), 'FM99999999999');

alter table Settings
    alter column id_settings type varchar(20);

create sequence settings_seq;

alter table Settings
    alter column id_settings set default to_char(nextval('settings_seq'::regclass), 'FM99999999999');

alter table j_user_module_role
    alter column id type varchar(20),
    alter column user_id type varchar(20),
    alter column module_id type varchar(20),
    alter column role_id type varchar(20);

create sequence j_user_module_role_seq;

alter table j_user_module_role
    alter column id set default to_char(nextval('j_user_module_role_seq'::regclass), 'FM99999999999');

alter table users
    alter column id type varchar(20);

create sequence users_seq;

alter table users
    alter column id set default to_char(nextval('users_seq'::regclass), 'FM99999999999');

alter table user_module
    alter column id type varchar(20);

create sequence user_module_seq;

alter table user_module
    alter column id set default to_char(nextval('user_module_seq'::regclass), 'FM99999999999');

alter table user_role
    alter column id type varchar(20);

create sequence user_role_seq;

alter table user_role
    alter column id set default to_char(nextval('user_role_seq'::regclass), 'FM99999999999');