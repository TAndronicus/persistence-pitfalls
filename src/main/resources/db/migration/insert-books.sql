--liquibase formatted sql
--changeset admin:1
create table book
(
    id    bigint primary key not null,
    title character varying
);
create sequence seq
    increment by 50;
insert into book (id, title)
values (nextval('seq'), 'Functional Programming for Mortals');
