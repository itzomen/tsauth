-- create db tsauth with the following tables
-- To delete all rows of a table DELETE FROM Users

create table users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table categories(
category_id integer primary key not null,
user_id integer not null,
title varchar(20) not null,
description varchar(50) not null
);
alter table categories add constraint cat_users_fk
foreign key (user_id) references users(user_id);

create sequence users_seq increment 1 start 1;
create sequence categories_seq increment 1 start 1;
