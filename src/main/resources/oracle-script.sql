create table users
(
    id            integer   not null
        primary key,
    email         varchar(256),
    date_of_birth timestamp not null -- also without constraint
);
create index users_by_date_ind on users (date_of_birth);

explain plan for
select *
from dual
where '1990-01-01' not in (
    select date_of_birth
    from users
);

select ID, OPERATION, OPTIONS, CPU_COST, IO_COST
from plan_table;
