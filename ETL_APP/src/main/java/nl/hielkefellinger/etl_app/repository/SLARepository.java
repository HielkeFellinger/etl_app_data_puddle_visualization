package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.SLA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SLARepository extends JpaRepository<SLA, UUID> {
}
