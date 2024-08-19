drop table if exists book_store_mapping;
drop table if exists book_tag_mapping;
drop table if exists book;
drop table if exists tag;
drop table if exists author;
drop table if exists store;

create table store
(
    id      bigint generated always as identity primary key,
    version int         not null,
    name    varchar(50) not null,
    website varchar(100)
);

create table author
(
    id     bigint generated always as identity primary key,
    name   varchar(50) not null
);

create table tag
(
    id   bigint generated always as identity primary key,
    name varchar(50) not null
);

create unique index idx_tag_name ON tag (name);

create table book
(
    id        bigint generated always as identity primary key,
    name      varchar(50) not null,
    website   varchar(100),
    rating    int         not null,
    author_id bigint
);

alter table book
    add constraint fk_book__author foreign key (author_id) references author (id);

create table book_tag_mapping
(
    book_id bigint not null,
    tag_id  bigint not null,
    primary key (book_id, tag_id)
);

create index idx_book_tag_mapping_tag on book_tag_mapping(tag_id, book_id);

alter table book_tag_mapping
    add constraint fk_book_tag_mapping__book foreign key (book_id) references book (id);

alter table book_tag_mapping
    add constraint fk_book_tag_mapping__tag foreign key (tag_id) references tag (id);

create table book_store_mapping
(
    book_id  bigint not null,
    store_id bigint not null,
    primary key (book_id, store_id)
);

alter table book_store_mapping
    add constraint fk_book_store_mapping__book foreign key (book_id) references book (id);

alter table book_store_mapping
    add constraint fk_book_author_mapping__store foreign key (store_id) references store (id);