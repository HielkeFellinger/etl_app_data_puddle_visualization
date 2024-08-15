package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.KPI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KPIRepository extends JpaRepository<KPI, UUID> {
}
