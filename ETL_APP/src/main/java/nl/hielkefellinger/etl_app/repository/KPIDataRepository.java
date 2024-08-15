package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.KPIData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KPIDataRepository extends JpaRepository<KPIData, KPIData.KPIDataKey> {
}
