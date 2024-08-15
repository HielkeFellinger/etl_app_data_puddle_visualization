package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, SensorData.SensorDataKey> {
}
