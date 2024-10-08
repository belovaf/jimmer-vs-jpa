insert into store (version, name, website) values (0, 'Amazon', 'www.amazon.com');
insert into store (version, name, website) values (0, 'Читай Город', 'www.chitai-gorod.ru');

insert into author(name) values ('Bob Martin');
insert into author(name) values ('Martin Fowler');
insert into author(name) values ('Kavita Devgan');

insert into book (name, rating, author_id) values ('Clean Code', 8, 1);
insert into book (name, rating, author_id) values ('Refactoring', 6, 2);
insert into book (name, rating, author_id) values ('Patterns of Enterprise Application Architecture', 9, 2);
insert into book (name, rating, author_id) values ('500 recipes', 5, 3);
insert into book (name, rating, author_id) values ('Bob Martin autobiography', 7, 1);

insert into tag(name) values ('IT');
insert into tag(name) values ('architecture');

insert into book_store_mapping(book_id, store_id) values (1, 1);
insert into book_store_mapping(book_id, store_id) values (2, 1);
insert into book_store_mapping(book_id, store_id) values (3, 1);

insert into book_store_mapping(book_id, store_id) values (2, 2);
insert into book_store_mapping(book_id, store_id) values (3, 2);

insert into book_tag_mapping(book_id, tag_id) values (1,1);
insert into book_tag_mapping(book_id, tag_id) values (2,1);
insert into book_tag_mapping(book_id, tag_id) values (3,1);

insert into book_tag_mapping(book_id, tag_id) values (3,2);