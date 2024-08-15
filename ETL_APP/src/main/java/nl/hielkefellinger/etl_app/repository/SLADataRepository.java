package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.SLAData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SLADataRepository extends JpaRepository<SLAData, SLAData.SLADataKey> {
}
