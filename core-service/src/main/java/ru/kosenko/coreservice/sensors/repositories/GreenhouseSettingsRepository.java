package ru.kosenko.coreservice.sensors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.coreservice.sensors.entities.GreenhouseSettings;

import java.util.Optional;

@Repository
public interface GreenhouseSettingsRepository extends JpaRepository<GreenhouseSettings, Long> {
    Optional<GreenhouseSettings> findByGreenhouseId(Long greenhouseId);
}
