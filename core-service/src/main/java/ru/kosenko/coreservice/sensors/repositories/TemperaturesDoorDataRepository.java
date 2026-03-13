package ru.kosenko.coreservice.sensors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.coreservice.sensors.entities.TemperaturesDoorData;

@Repository
public interface TemperaturesDoorDataRepository extends JpaRepository<TemperaturesDoorData, Long> {}
