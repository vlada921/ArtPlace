drop table if exists artplace.ap_users cascade;
drop table if exists artplace.ap_authorities cascade;
drop table if exists artplace.ap_user_authorities cascade;
drop table if exists artplace.ap_publics cascade;
drop table if exists artplace.ap_publications cascade;
drop table if exists artplace.ap_files cascade;
drop table if exists artplace.ap_publication_files cascade;
drop table if exists artplace.ap_registration_confirmation cascade;
drop table if exists artplace.ap_params cascade;

-- DDL

create schema if not exists artplace;

create extension if not exists "uuid-ossp";

-- Functions

create or replace function is_real_length_between(
    checked_string varchar,
    min_length integer default 2,
    max_length integer default 128
) returns boolean as $$
    begin
        return length(checked_string) between min_length and max_length
            and length(trim(checked_string)) >= min_length;
    end;
$$ language plpgsql;

create or replace function max_user_publics_check_procedure() returns trigger as $$
    declare
        maxUserPublics integer;
        currentUserPublics integer;
    begin
        select value into maxUserPublics from artplace.ap_params where key = 'maxUserPublics';

        select count(1) into currentUserPublics
        from artplace.ap_publics p
        where p.owner_id = NEW.owner_id;

        if currentUserPublics >= maxUserPublics then
            raise exception 'Owner (id = %) reached publics limit', NEW.owner_id;
        end if;

        return NEW;

        exception
        when no_data_found then
            return NEW; -- do nothing (i.e. maxUserPublics is not limited)
    end;
$$ language plpgsql;

create table if not exists artplace.ap_users(
    id uuid default public.uuid_generate_v4() primary key,
    name varchar(128),
    gender varchar(64),
    email varchar(128),
    birthday date,
    password varchar,
    constraint ap_users_name_length_check
        check (
            is_real_length_between(name)
        )
);

create table if not exists artplace.ap_registration_confirmation(
    user_id uuid default public.uuid_generate_v4() primary key,
    created_when timestamp default now() not null,
    confirmed_when timestamp,
    token varchar not null default public.uuid_generate_v4(),
    expires_when timestamp,
    constraint ap_registration_confirmation_check_dates
        check (confirmed_when is null or confirmed_when > ap_registration_confirmation.created_when),
    constraint ap_registration_confirmation_user_exists
        foreign key (user_id) references artplace.ap_users(id),
    constraint ap_registration_confirmation_unique_token
        unique (token)
);

create table if not exists artplace.ap_publics(
    id uuid default public.uuid_generate_v4() primary key,
    name varchar(128),
    owner_id uuid not null,
    constraint ap_publics_name_length_check
       check (
           is_real_length_between(name)
       ),
    constraint ap_publics_owner_fk
        foreign key (owner_id) references artplace.ap_users(id)
);

create table if not exists artplace.ap_publications(
    id uuid default public.uuid_generate_v4() primary key,
    public_id uuid not null,
    title varchar(128) not null,
    publication_text varchar(4096) not null,
    constraint ap_publications_public_fk
        foreign key (public_id) references artplace.ap_publics(id),
    constraint ap_publications_title_length_check
        check (is_real_length_between(title)),
    constraint ap_publications_body_length_check
        check (is_real_length_between(publication_text, 2, 4096))
);

create table if not exists artplace.ap_files(
    id uuid default public.uuid_generate_v4() primary key,
    uri varchar(4096) not null,
    constraint ap_files_url_length_check check (is_real_length_between(uri))
);

create table artplace.ap_publication_files(
    publication_id uuid,
    file_id uuid,
    primary key (publication_id, file_id),
    constraint ap_publication_files_publication_fk foreign key (publication_id) references artplace.ap_publications(id),
    constraint ap_publication_files_file_fk foreign key (file_id) references artplace.ap_files(id)
);

create table if not exists artplace.ap_params(
    key varchar primary key,
    value varchar
);

create table if not exists artplace.ap_authorities(
    name varchar(128) primary key
);

create table if not exists artplace.ap_user_authorities(
    user_id uuid,
    authority_name varchar(128),
    primary key (user_id, authority_name),
    constraint ap_user_authorities_user_fk
        foreign key (user_id) references artplace.ap_users(id),
    constraint ap_user_authorities_authority_fk
        foreign key (authority_name) references artplace.ap_authorities(name)
);

create table if not exists artplace.ap_subscr_tariffs(
    id uuid primary key,
    name varchar(128) not null
);

create table if not exists artplace.ap_publics_subscriptions(
    user_id uuid,
    public_id uuid,
    tariff_id uuid not null,
    constraint ap_publics_subscriptions_user_fk
        foreign key (user_id) references artplace.ap_users(id),
    constraint ap_publics_subscriptions_public_fk
        foreign key (public_id) references artplace.ap_publics(id),
    constraint ap_publics_subscriptions_tariff_fk
        foreign key (tariff_id) references artplace.ap_subscr_tariffs(id),
    constraint ap_publics_subscriptions_pk
        primary key (user_id, public_id)
);

-- Triggers

create trigger max_user_publics_check_trigger
    before insert or update of owner_id on artplace.ap_publics
    for each row
execute procedure max_user_publics_check_procedure();

-- DML

insert into artplace.ap_params values
    ('maxUserPublics', 128);
insert into artplace.ap_authorities values
    ('REGISTRATION_CONFIRMATION_PENDING'),
    ('REGISTRATION_CONFIRMED'),
    ('ADMIN');
insert into artplace.ap_users values (
    default,
    'admin',
    null,
    'admin@artplace.com',
    now(),
    '$2y$10$/RGz1iys40SQS3Nt7DeB9.P7vzk.6e3ecGdF3d0hLGuaFNvk0ad6m'/*BCrypt(2y, 10) : admin*/
);
insert into artplace.ap_user_authorities values
    ((select id from artplace.ap_users where name = 'admin'), 'ADMIN'),
    ((select id from artplace.ap_users where name = 'admin'), 'REGISTRATION_CONFIRMED');

insert into artplace.ap_subscr_tariffs values
    (public.uuid_generate_v4(), 'FREE'),
    (public.uuid_generate_v4(), 'PAID');

set search_path = "artplace";
