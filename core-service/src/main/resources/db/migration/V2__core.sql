create table smart_home
(
    id     bigserial primary key,
    title varchar(100),
    type varchar(100),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);

insert into smart_home (title, type)
values ('Теплица с автоматикой', 'AUTO'),
       ('Полив гистерезис', 'OUTDOOR'),
       ('Полив по влажности', 'HUMIDITY'),
       ('Показания без ответа' , 'INDICATIONS'),
       ('Счетчик импульсов' , 'PULSE_COUNTERS'),
       ('Показания без ответа' , 'INDICATIONS'),
       ('Навигация' , 'NAVIGATIONS');

create table green_house
(
    id     bigserial primary key,
    smart_home_id INTEGER NOT NULL,
    title varchar(100),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);

CREATE INDEX idx_greenhouses_smart_home_id ON green_house(smart_home_id);

insert into green_house (smart_home_id, title)
values (1, 'Теплица с помидорами'),
       (1, 'Теплица с перцами'),
       (2, 'Грядка огурцы'),
       (2, 'Грядка арбузы'),
       (2, 'Грядка капуста'),
       (4, 'датчик температуры на улице'),
       (5, 'Счетчик газа');

create table temperatures
(
    id        bigserial primary key,
    green_house_id INTEGER NOT NULL,
    name  varchar(100),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
);
CREATE INDEX idx_temperatures_greenhouse_id ON temperatures(green_house_id);

insert into temperatures (green_house_id, name)
values (1, 'Температура помидоры'),
       (2, 'Температура перец'),
       (6, 'Температура на улице');

CREATE TABLE humidity (
                          id bigserial PRIMARY KEY,
                          green_house_id INTEGER NOT NULL,
                          name VARCHAR(100),
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

insert into humidity (green_house_id, name)
values (1, 'Влажность 1'),
       (1, 'Влажность 2'),
       (1, 'Влажность 3'),
       (1, 'Влажность 4'),
       (2, 'Влажность 1'),
       (3, 'Влажность 2'),
       (4, 'Влажность 3'),
       (5, 'Влажность 4');

CREATE INDEX idx_humidity_greenhouse_id ON humidity(green_house_id);

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

CREATE TABLE temperature_door_data (
                               id BIGSERIAL PRIMARY KEY,
                               temperatures_id INTEGER NOT NULL REFERENCES temperatures(id) ON DELETE CASCADE,
                               temperature_door NUMERIC(5, 2) NOT NULL,
                               recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_temperature_door_data_sid_time ON temperature_door_data(temperatures_id, recorded_at DESC);