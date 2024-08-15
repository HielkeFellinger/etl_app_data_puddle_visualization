package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.Contract;
import nl.hielkefellinger.etl_app.domain.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Optional<Contract> findByName(String name);

    @Query("select c from Contract c " +
        "LEFT JOIN c.organisation o " +
        "WHERE c.organisation = :organisation")
    List<Contract> findAllByOrganisation(@Param("organisation") Organisation organisation);

    @Query("SELECT c FROM Contract c " +
        "WHERE c.startTime < local_datetime " +
        "AND (c.stopTime is null or c.stopTime > local_datetime)")
    List<Contract> findAllActiveContracts();
}
