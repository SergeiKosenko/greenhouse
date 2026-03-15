package ru.kosenko.coreservice.sensors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.coreservice.sensors.entities.Temperatures;

@Repository
public interface TemperaturesRepository extends JpaRepository<Temperatures, Long> {
}
