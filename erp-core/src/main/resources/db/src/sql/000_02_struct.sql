create table Counteragent
(
    id                bigserial primary key,
    counteragent_name varchar(100)                        not null,
    group_name        varchar(100),
    first_name        varchar(100),
    surname           varchar(100),
    patronymic        varchar(100),
    phone_number      varchar(100),
    mail              varchar(100),
    address           varchar(100),
    status            integer   default 0                 not null,
    creation_date     timestamp default current_timestamp not null,
    deactivation_date timestamp,
    deactivated       integer   default 0
);

create table Contract
(
    id                bigserial primary key,
    id_counteragent   integer                             not null references Counteragent (id),
    id_type_agreement integer                             not null,
    contract_date     date,
    contract_number   varchar(100),
    contract_subject  varchar(100),
    status            integer   default 0                 not null,
    creation_date     timestamp default current_timestamp not null,
    deactivation_date timestamp,
    deactivated       integer   default 0
);

create table KS
(
    id                bigserial primary key,
    id_contract       integer                             not null references Contract (id),
    ks_date           date,
    ks_number         varchar(100),
    ks_sum            decimal,
    garant_sum        decimal,
    garant_date       date,
    payment_status    integer   default 0                 not null,
    creation_date     timestamp default current_timestamp not null,
    deactivation_date timestamp,
    deactivated       integer   default 0
);

create table Settings
(
    id_settings        bigserial primary key,
    user_theme_color   integer   default 0                 not null,
    user_theme_pattern integer   default 0                 not null,
    user_theme_zoom    decimal   default 1.0               not null,
    status             integer   default 0                 not null,
    creation_date      timestamp default current_timestamp not null,
    deactivated        integer   default 0                 not null,
    deactivation_date  timestamp default current_timestamp
);

create table j_user_module_role
(
    id                bigserial primary key,
    user_id           integer,
    module_id         integer,
    role_id           integer,
    creation_date     timestamp default current_timestamp,
    deactivation_date timestamp,
    active_status     integer
);

create table users
(
    id                      bigserial primary key,
    user_name               varchar(50)                         not null,
    password                varchar(100)                        not null,
    first_name              varchar(50)                         not null,
    surname                 varchar(50)                         not null,
    patronymic              varchar(50)                         not null,
    phone_number            varchar(50)                         not null,
    mail                    varchar(50),
    employee_position       varchar(100),
    account_non_expired     bit,
    account_non_locked      bit,
    credentials_non_expired bit,
    enabled                 bit,
    creation_date           timestamp default current_timestamp not null,
    deactivation_date       timestamp,
    active_status           integer   default 0
);

create table user_module
(
    id                bigserial primary key,
    name              varchar(30)                         not null unique,
    creation_date     timestamp default current_timestamp not null,
    deactivation_date timestamp,
    deactivated       integer   default 0
);


create table user_role
(
    id                bigserial primary key,
    name              varchar(30)                         not null unique,
    create_date       timestamp default current_timestamp not null,
    deactivation_date timestamp,
    deactivated       integer   default 0
);
