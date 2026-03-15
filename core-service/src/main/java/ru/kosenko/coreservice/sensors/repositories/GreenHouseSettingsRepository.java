package ru.kosenko.coreservice.sensors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.coreservice.sensors.entities.GreenHouseSettings;

import java.util.Optional;

@Repository
public interface GreenHouseSettingsRepository extends JpaRepository<GreenHouseSettings, Long> {
    Optional<GreenHouseSettings> findByGreenHouseId(Long greenHouseId);
}
