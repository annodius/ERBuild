alter table ks
    alter column payment_status set default null;

alter table ks
    alter column payment_status type bool using payment_status::bool;

alter table ks
    alter column payment_status set default false;

