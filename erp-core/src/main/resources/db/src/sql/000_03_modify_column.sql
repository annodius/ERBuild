alter table users
    alter column account_non_expired type bool
        using account_non_expired::int::bool,
    alter column account_non_locked type bool
        using account_non_locked::int::bool,
    alter column credentials_non_expired type bool
        using credentials_non_expired::int::bool,
    alter column enabled type bool
        using enabled::int::bool;