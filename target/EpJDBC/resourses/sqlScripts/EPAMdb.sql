create schema epamdb collate utf8mb4_0900_ai_ci;

create table usertype
(
  id int auto_increment
    primary key,
  name varchar(50) null
);

create table user
(
  id int auto_increment
    primary key,
  name varchar(50) null,
  lastName varchar(50) not null,
  balance double default 0 null,
  userType_id int not null,
  constraint user_ibfk_1
    foreign key (userType_id) references usertype (id)
);

create index userType_id
  on user (userType_id);

