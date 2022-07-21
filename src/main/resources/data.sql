insert into Book (isbn, title, published) values ('978-1441529961', 'A Critique of Pure Physics:',
                                                  '2020-3-5');
insert into Book (isbn, title, published) values ('978-1292183398', 'Pure Mathematics', '2000-1-1');

insert into Users (username, name, institution, department)
values ('dev', 'Dev Account', 'Imperial College London', 'Computing');
insert into Users (username, name, institution, department)
values ('ef420_icl', 'Emil Filip', 'Imperial College London', 'Computing');
insert into Users (username, name, institution, department)
values ('Anand5329', 'Anand Doshi', 'Imperial College London', 'Computing');
insert into Users (username, name, institution, department)
values ('LJRex', 'Louis Heal', 'Imperial College London', 'Computing');
insert into Users (username, name, institution, department)
values ('Anjey', 'Andrii Verveha', 'Imperial College London', 'Computing');

insert into Ownership (owner_id, book_id, totalCopies, currentCopies)
values ((select id from Users where username='ef420_icl'),
    (select id from Book where isbn='978-1441529961'),
    1, 1);
insert into Ownership (owner_id, book_id, totalCopies, currentCopies)
values ((select id from Users where username='dev'),
    (select id from Book where isbn='978-1292183398'),
    1, 1);

insert into Ownership (owner_id, book_id, totalCopies, currentCopies)
values ((select id from Users where username='Anand5329'),
    (select id from Book where isbn='978-1441529961'),
    1, 1);

