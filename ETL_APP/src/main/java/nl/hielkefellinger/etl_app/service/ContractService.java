package nl.hielkefellinger.etl_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Contract;
import nl.hielkefellinger.etl_app.domain.Organisation;
import nl.hielkefellinger.etl_app.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public Contract getContractById(UUID contractId) {
        return contractRepository.findById(contractId).orElse(null);
    }

    public Contract getContractByName(String name) {
        return contractRepository.findByName(name).orElse(null);
    }

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

    public List<Contract> getAllActiveContracts() {
        return contractRepository.findAllActiveContracts();
    }

    public List<Contract> getContractByOrganisation(Organisation organisation) {
        return contractRepository.findAllByOrganisation(organisation);
    }

    public Contract createNewContract(Contract contract) {
        return contractRepository.save(contract);
    }
}
