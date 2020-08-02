--liquibase formatted sql
--changeset admin:3
create table award
(
    name    character varying,
    details character varying,
    year    int,
    book_id bigint
);
insert into award (name, details, year, book_id)
values ('Dijkstra award', null, 2010, 2),
       ('Kleene award', 'Most difficult book', 2011, 2),
       ('Goedel award', null, 2010, 2),
       ('Readers choice award', 'I am going to read it some day', 2013, 2);
