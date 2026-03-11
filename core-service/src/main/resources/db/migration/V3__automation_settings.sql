DROP TABLE IF EXISTS greenhouse_settings CASCADE;

CREATE TABLE greenhouse_settings (
                                     id BIGSERIAL PRIMARY KEY,
                                     greenhouse_id INTEGER NOT NULL UNIQUE REFERENCES greenhouse(id) ON DELETE CASCADE,

    -- Температурные пороги (Форточки и Двери)
                                     temp_open_window NUMERIC(4, 1) DEFAULT 25.0,
                                     temp_close_window NUMERIC(4, 1) DEFAULT 22.0,
                                     temp_open_door NUMERIC(4, 1) DEFAULT 28.0,
                                     temp_close_door NUMERIC(4, 1) DEFAULT 24.0,

    -- Полив: Гистерезис влажности
                                     min_humidity_pct NUMERIC(4, 1) DEFAULT 35.0, -- Порог включения
                                     max_humidity_pct NUMERIC(4, 1) DEFAULT 65.0, -- Порог выключения

    -- Полив: Расписание (Периодичность)
                                     watering_start_time TIME DEFAULT '06:00:00',  -- Время первого полива
                                     watering_interval_hours INTEGER DEFAULT 24,    -- Интервал (24 = раз в сутки, 8 = трижды)
                                     watering_duration_min INTEGER DEFAULT 20,      -- Сколько минут лить воду

    -- Текущие состояния и метаданные
                                     window_open BOOLEAN DEFAULT false,
                                     door_open BOOLEAN DEFAULT false,
                                     watering_on BOOLEAN DEFAULT false,
                                     last_watering_time TIMESTAMP WITH TIME ZONE,
                                     is_auto_mode BOOLEAN DEFAULT true,
                                     updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Начальные данные (Теплицы 1 и 2)
INSERT INTO greenhouse_settings (greenhouse_id, watering_start_time, watering_interval_hours)
VALUES (1, '06:00:00', 12), (2, '07:00:00', 24);

