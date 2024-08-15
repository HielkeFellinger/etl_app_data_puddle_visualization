package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.enums.SensorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {

    public Sensor findByName(String name);

    public List<Sensor> findAllBySensorGroup(SensorGroup group);
}
