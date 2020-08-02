--liquibase formatted sql
--changeset admin:2
create table review
(
    id             bigint primary key
        not null,
    grade          int,
    review_comment character varying,
    book_id        bigint
        not null
        references book
);
insert into review (id, grade, review_comment, book_id)
values (1, 3, 'I have seen better', 1),
       (2, 4, 'Good but difficult', 2),
       (3, 5, 'Best I have ever read', 1),
       (4, 2, 'I did not like the cover', 3),
       (5, 4, 'So good it was too short', 4),
       (6, 2, 'Some day I will understand this', 2),
       (7, 5, 'Must-read for every Java backend developer', 5);
