package nl.hielkefellinger.etl_app.repository;

import nl.hielkefellinger.etl_app.domain.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {

    Optional<Organisation> findByName(String name);
}
