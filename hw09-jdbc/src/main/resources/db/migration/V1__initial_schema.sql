create table manager
(
    no   bigserial,
    label varchar(50)
);

create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);