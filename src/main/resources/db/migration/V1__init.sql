create table categories (id bigserial primary key, title varchar(255));
insert into categories (title)
values
('Food'),
('Electronic'),
('Book');

create table products (id bigserial primary key, title varchar(255), price int, category_id bigint references categories (id));
insert into products (title, price, category_id)
values
('Milk', 95, 1),
('Bread', 25, 1),
('Cheese', 360, 1);

create table authors (id bigserial primary key, name varchar(255));
insert into authors (name)
values
('Harper_Lee'),
('William_Shakespeare'),
('Jane_Austen'),
('Suzanne_Collins');

create table books (id bigserial primary key, title varchar(255), price int, category_id bigint references categories(id), author_id bigint references authors(id));
insert into books (title, price, category_id, author_id)
values
('To Kill a Mockingbird', 10, 3, 1),
('Go Set a Watchman', 12, 3, 1),
('Romeo and Juliet', 30, 3, 2),
('Pride and Prejudice', 15, 3, 3);
