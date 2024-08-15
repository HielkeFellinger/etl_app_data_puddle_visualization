package nl.hielkefellinger.etl_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Organisation;
import nl.hielkefellinger.etl_app.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public Organisation getOrganisationById(UUID organisationId) {
        return organisationRepository.findById(organisationId).orElse(null);
    }

    public Organisation getOrganisationByName(String name) {
        return organisationRepository.findByName(name).orElse(null);
    }

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation createNewOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

}
