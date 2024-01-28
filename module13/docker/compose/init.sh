#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  create table public.users (
    id serial primary key,
    name varchar not null,
    age integer not null
  );
EOSQL