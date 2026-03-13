create table roles
(
    id            bigserial primary key,
    name          varchar(100) not null,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);

create table users
(
    id             bigserial primary key,
    username       varchar(100),
    password       varchar(100) not null,
    first_name     varchar(100) ,
    last_name      varchar(100) ,
    email          varchar(100) not null,
    phone          varchar(100) ,
    active         boolean default false,
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at     TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);

create table users_roles
(
    user_id        bigint not null references users (id),
    role_id       bigint not null references roles (id),
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at     TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name) values
                             ('ROLE_USER'),
                             ('ROLE_ADMIN'),
                             ('ROLE_SUPER_ADMIN'),
                             ('ROLE_MANAGER'),
                             ('ROLE_STOREKEEPER');

insert into users (username, password, last_name, first_name, email, phone, active) values
                                                                                        ( 'superAdmin', '$2a$10$y1Nfp4mngCpPFPUpGoe6sezeOZMODZI.Ww88le3sApcp69kCtU7fC', 'Иванов', 'Иван', 'superadmin@mail.ru', '8 (999)-989-77-22', true ),
                                                                                        ( 'admin', '$2a$10$y1Nfp4mngCpPFPUpGoe6sezeOZMODZI.Ww88le3sApcp69kCtU7fC', 'Петров', 'Петр', 'admin@mail.ru', '8 (999)-989-77-33', true ),
                                                                                        ( 'user', '$2a$10$y1Nfp4mngCpPFPUpGoe6sezeOZMODZI.Ww88le3sApcp69kCtU7fC', 'Петров', 'Петр', 'admin@mail.ru', '8 (999)-989-77-33', true ),
                                                                                        ( 'sergonian', '$2a$10$y1Nfp4mngCpPFPUpGoe6sezeOZMODZI.Ww88le3sApcp69kCtU7fC', 'Сидоров', 'Василий', 'user@mail.ru', '8 (999)-989-77-11', true ),
                                                                                        ( 'vasya', '$2a$10$y1Nfp4mngCpPFPUpGoe6sezeOZMODZI.Ww88le3sApcp69kCtU7fC', 'Сидоров', 'Василий', 'user@mail.ru', '8 (999)-989-77-11', true );

insert into users_roles (user_id, role_id) values
                                               (1, 3),
                                               (2, 2),
                                               (3, 1),
                                               (4, 5),
                                               (5, 1);

