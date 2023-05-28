DROP TABLE IF EXISTS books;
create table public.books (
  id                  bigserial primary key not null,
  isbn                varchar(255) not null,
  name                varchar(255),
  author_name          varchar(255),
  price               float8,
  quantity            int not null,
  created_date        timestamp not null,
  last_modified_date  timestamp not null,
  version             integer not null
);