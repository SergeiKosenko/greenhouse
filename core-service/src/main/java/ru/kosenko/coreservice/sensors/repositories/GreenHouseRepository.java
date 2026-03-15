package ru.kosenko.coreservice.sensors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.coreservice.sensors.entities.GreenHouse;

@Repository
public interface GreenHouseRepository extends JpaRepository<GreenHouse, Long> {
}
