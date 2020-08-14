-- uses index
explain
select *
from users
where is_premium;
-- misses index
explain
select *
from users
where not is_premium;

-- wykonywanie działań na indeksowanej kolumnie
-- źle
explain
select *
from users
where current_timestamp - date_of_birth > '18 years'
-- dobrze
explain
select *
from users
where date_of_birth < current_timestamp - interval '18 years';

-- sargable
-- źle
explain
select *
from users
where email like '%abc%';
-- źle
explain
select *
from users
where email ~ 'abc';
-- dobrze
explain
select *
from users
where email like 'abc%';
-- dobrze -> tsvector

-- korzystanie z dwóch indeksów
explain
select *
from users
where email between 'a' and 'b'
  and date_of_birth between '1990-01-01' and '1991-01-01';
create index users_by_date_of_birth_and_email on users (date_of_birth, email);
analyse users;

-- statystyka a indeks
select count(*) filter ( where is_premium ) :: double precision / count(*)
from users;
-- uses index
explain
select *
from users
where is_premium;
-- misses index
explain
select *
from users
where not is_premium;

-- sprawdzanie istnienia
insert into review (id, grade, review_comment, book_id)
select nextval('seq'), (random() * 5 + 1)::int, md5('' || series.i), mod(series.i, 4) + 1
from generate_series(1, 1000000) as series(i);

explain analyse
select *
from book
where id not in (
    select distinct book_id
    from review
);
explain analyse
select distinct b.*
from book b
         inner join review r on b.id = r.book_id;
explain analyse
select distinct b.*
from book b
         left join review r on b.id = r.book_id
where r.id is not null;
explain analyse
select *
from book b
where (
          select count(*)
          from review r
          where r.book_id = b.id
      ) = 0;
explain analyse
select *
from book b
where exists(
              select *
              from review r
              where r.book_id = b.id
          );

-- funkcje okna
create table income
(
    income_timestamp timestamp        not null,
    income_value     double precision not null
);
insert into income
select series.t, random() * 100
from generate_series('2000-01-01', current_timestamp, '1 hour') series(t);
delete
from income
where 1 = 1;
select count(*)
from income;

-- miesięczne zyski
select date_trunc('month', income_timestamp), sum(income_value)
from income
group by date_trunc('month', income_timestamp);

select *
from income;

explain analyse
select current_month.d, current_month.v - previous_month.v
from (
         select date_trunc('month', income_timestamp), sum(income_value)
         from income
         group by date_trunc('month', income_timestamp)
     ) current_month(d, v)
         inner join (
    select date_trunc('month', income_timestamp), sum(income_value)
    from income
    group by date_trunc('month', income_timestamp)
) previous_month(d, v) on current_month.d = previous_month.d + interval '1 month'
order by current_month.d desc;

explain analyse
with month_agg(d, v) as (
    select date_trunc('month', income_timestamp), sum(income_value)
    from income
    group by date_trunc('month', income_timestamp)
)
select curr.d, curr.v - prev.v
from month_agg curr
         inner join month_agg prev on curr.d = prev.d + '1 month'
order by curr.d desc;

explain analyse
with month_agg(d, v) as (
    select date_trunc('month', income_timestamp), sum(income_value)
    from income
    group by date_trunc('month', income_timestamp)
)
select d, v - lag(v) over (order by d)
from month_agg
order by d desc;

explain analyse
with month_agg(d, v) as (
    select date_trunc('month', income_timestamp), max(income_value)
    from income
    group by date_trunc('month', income_timestamp)
)
select i.income_timestamp, i.income_value
from income i
         inner join month_agg m
                    on (date_trunc('month', i.income_timestamp), i.income_value) = (m.d, m.v)
order by income_timestamp desc;

explain analyse
select distinct first_value(income_timestamp) over (monthly),
                first_value(income_value) over (monthly)
from income
    window monthly as (
        partition by date_trunc('month', income_timestamp)
        order by income_value desc
        )
order by first_value(income_timestamp) over (monthly) desc;
