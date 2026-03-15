DROP TABLE IF EXISTS green_house_settings CASCADE;

CREATE TABLE green_house_settings (
                                     id BIGSERIAL PRIMARY KEY,
                                     green_house_id INTEGER NOT NULL UNIQUE REFERENCES green_house(id) ON DELETE CASCADE,

    -- Температурные пороги (Форточки и Двери)
                                     temp_open_window NUMERIC(4, 2) DEFAULT 25.00,
                                     temp_close_window NUMERIC(4, 2) DEFAULT 22.00,
                                     temp_open_door NUMERIC(4, 2) DEFAULT 28.00,
                                     temp_close_door NUMERIC(4, 2) DEFAULT 24.00,

    -- ПОДОГРЕВ: Гистерезис температуры подогрева
                                     temp_turn_on_heater NUMERIC(4, 2) DEFAULT 5.00,  -- Включить обогрев (холодно)
                                     temp_turn_off_heater NUMERIC(4, 2) DEFAULT 10.00, -- Выключить обогрев (тепло)


    -- Полив: Гистерезис влажности
                                     min_humidity_pct NUMERIC(4, 2) DEFAULT 35.00, -- Порог включения
                                     max_humidity_pct NUMERIC(4, 2) DEFAULT 65.00, -- Порог выключения

    -- Полив: Расписание (Периодичность)
                                     watering_start_time TIME DEFAULT '08:00:00',  -- Время первого полива
                                     watering_interval_hours INTEGER DEFAULT 8,    -- Интервал (24 = раз в сутки, 8 = трижды)
                                     watering_duration_min INTEGER DEFAULT 60,      -- Сколько минут лить воду

    -- Текущие состояния и метаданные
                                     window_open BOOLEAN DEFAULT false,
                                     door_open BOOLEAN DEFAULT false,
                                     watering_on BOOLEAN DEFAULT false,
                                     heater_on BOOLEAN DEFAULT false,

                                     last_watering_time TIMESTAMP WITH TIME ZONE,
                                     is_auto_mode BOOLEAN DEFAULT true,
                                     updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Начальные данные (Теплицы 1, 2 и грядок)
INSERT INTO green_house_settings (green_house_id, watering_start_time, watering_interval_hours, temp_turn_on_heater, temp_turn_off_heater)
VALUES
    (1, '06:00:00', 4, 5.00, 10.00),
    (2, '06:00:00', 1, 5.00, 10.00),
    (3, '06:00:00', 2, 5.00, 10.00),
    (4, '06:00:00', 3, 5.00, 10.00),
    (5, '06:00:00', 1, 5.00, 10.00),
    (6, '07:00:00', 1, 4.00, 9.00);