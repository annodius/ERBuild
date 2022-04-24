create table Counteragent
(
    id                uuid     default newid() primary key,
    counteragent_name varchar(100)               not null,
    group_name        varchar(100),
    first_name        varchar(100),
    surname           varchar(100),
    patronymic        varchar(100),
    phone_number      varchar(100),
    mail              varchar(100),
    address           varchar(100),
    status            int      default 0         not null,
    creation_date     datetime default getdate() not null,
    deactivation_date datetime,
    deactivated       int      default 0
);

create table Contract
(
    id                uuid     default newid() primary key,
    id_counteragent   uuid                       not null references Counteragent (id),
    id_type_agreement int                        not null,
    contract_date     date,
    contract_number   varchar(100),
    contract_subject  varchar(100),
    status            int      default 0         not null,
    creation_date     datetime default getdate() not null,
    deactivation_date datetime,
    deactivated       int      default 0
);

create table KS
(
    id                uuid     default newid() primary key,
    id_contract       uuid                       not null references Contract (id),
    ks_date           date,
    ks_number         nvarchar(100),
    ks_sum            decimal,
    garant_sum        decimal,
    garant_date       date,
    payment_status    int      default 0         not null,
    creation_date     datetime default getdate() not null,
    deactivation_date datetime,
    deactivated       int      default 0
);

create table Settings
(
    id_settings        uuid     default newid() primary key,
    user_theme_color   int      default 0         not null,
    user_theme_pattern int      default 0         not null,
    user_theme_zoom    decimal  default 1.0       not null,
    status             int      default 0         not null,
    creation_date      datetime default getdate() not null,
    deactivated        int      default 0         not null,
    deactivation_date  datetime default getdate()
);

create table j_user_module_role
(
    id                uuid     default newid() primary key,
    user_id           uuid,
    module_id         uuid,
    role_id           uuid,
    creation_date     datetime default getdate(),
    deactivation_date datetime,
    active_status     int
);

create table users
(
    id                      uuid     default newid() primary key,
    user_name               varchar(50)                not null,
    password                varchar(100)               not null,
    first_name              varchar(50)                not null,
    surname                 varchar(50)                not null,
    patronymic              varchar(50)                not null,
    phone_number            varchar(50)                not null,
    mail                    varchar(50),
    employee_position       varchar(100),
    account_non_expired     bit,
    account_non_locked      bit,
    credentials_non_expired bit,
    enabled                 bit,
    creation_date           datetime default getdate() not null,
    deactivation_date       datetime,
    active_status           int      default 0
);

create table user_module
(
    id                uuid     default newid() primary key,
    name              varchar(30)                not null unique,
    creation_date     datetime default getdate() not null,
    deactivation_date datetime,
    deactivated       int      default 0
);


create table user_role
(
    id                uuid     default newid() primary key,
    name              varchar(30)                not null unique,
    create_date       datetime default getdate() not null,
    deactivation_date datetime,
    deactivated       int      default 0
);
