package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceInstance, UUID> {

    @Query("SELECT s FROM ServiceInstance s " +
        "LEFT JOIN s.contract c " +
        "WHERE c.startTime < local_datetime " +
        "AND (c.stopTime is null or c.stopTime > local_datetime)")
    List<ServiceInstance> findAllActiveServices();

    Optional<ServiceInstance> findByName(String name);
}
