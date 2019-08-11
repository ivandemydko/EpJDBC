create schema banka collate utf8mb4_0900_ai_ci;

create table user
(
  id int auto_increment
    primary key,
  name varchar(50) null,
  lastName varchar(50) not null,
  balance double default 0 null,
  userType_id int default 0 null
);

