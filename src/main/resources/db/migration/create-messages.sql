--liquibase formatted sql
--changeset admin:4
create table message
(
    id          bigint            not null
        primary key,
    title       character varying not null,
    content     character varying,
    sender_id   bigint            not null,
    receiver_id bigint            not null,
    read        boolean           not null
);
insert into message (id, title, content, sender_id, receiver_id, read)
values (1, 'Welcome letter', 'Witamy!', 0, 1, true),
       (2, 'Newsletter', 'W tym tygodniu nie wydano ciekawych książek', 0, 1, false),
       (3, 'Zaproszenie do znajomych', 'Pamiętasz mnie?', 2, 1, false),
       (4, 'Meeting', 'Join our table', 3, 1, false);
