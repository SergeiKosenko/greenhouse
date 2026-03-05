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

create table greenhouse
(
    id     bigserial primary key,
    title varchar(100),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);
insert into greenhouse (title)
values ('Теплица с помидорами'),
       ('Теплица с перцами'),
       ('датчик температуры на улице');

create table temperatures
(
    id        bigserial primary key,
    greenhouse_id INTEGER NOT NULL,
     //greenhouse (id),
    name  varchar(100),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);
CREATE INDEX idx_temperatures_greenhouse_id ON temperatures(greenhouse_id);

insert into temperatures (greenhouse_id, name)
values (1, 'Температура помидоры'),
       (2, 'Температура перец'),
       (3, 'Температура улица');

CREATE TABLE humidity (
                         id bigserial PRIMARY KEY,
                         greenhouse_id INTEGER NOT NULL, -- Привязка к объекту
                         name VARCHAR(100),        -- Например, "Кухня" или "Улица"
                         created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

insert into humidity (greenhouse_id, name)
values (1, 'Влажность 1'),
       (1, 'Влажность 2'),
       (1, 'Влажность 3'),
       (1, 'Влажность 4'),
       (2, 'Влажность 1'),
       (2, 'Влажность 2'),
       (2, 'Влажность 3'),
       (2, 'Влажность 4');

CREATE INDEX idx_humidity_greenhouse_id ON humidity(greenhouse_id);

CREATE TABLE temperatures_data (
                             id BIGSERIAL PRIMARY KEY,
                             temperatures_id INTEGER NOT NULL REFERENCES temperatures(id) ON DELETE CASCADE,
                             temperature NUMERIC(5, 2) NOT NULL,
                             recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_temperatures_data_sid_time ON temperatures_data(temperatures_id, recorded_at DESC);

CREATE TABLE humidity_data (
                                   id BIGSERIAL PRIMARY KEY,
                                   humidity_id INTEGER NOT NULL REFERENCES humidity(id) ON DELETE CASCADE,
                                   humidity NUMERIC(5, 2) NOT NULL,
                                   recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_humidity_data_sid_time ON humidity_data(humidity_id, recorded_at DESC);