--liquibase formatted sql
--changeset admin:1
create table book
(
    id    bigint primary key not null,
    title character varying
);
create sequence seq
    increment by 50
    start with 50;
insert into book (id, title)
values (1, 'Functional Programming for Mortals'),
       (2, 'The Art of Computer Programming'),
       (3, 'Database internals'),
       (4, 'Data intensive applications'),
       (5, 'High performance Java performance');
