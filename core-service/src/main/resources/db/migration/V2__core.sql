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
                          greenhouse_id INTEGER NOT NULL,
                          name VARCHAR(100),
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