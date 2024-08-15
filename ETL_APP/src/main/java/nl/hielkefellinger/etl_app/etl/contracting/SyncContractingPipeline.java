package nl.hielkefellinger.etl_app.etl.contracting;

import lombok.RequiredArgsConstructor;
import nl.hielkefellinger.etl_app.domain.Contract;
import nl.hielkefellinger.etl_app.domain.Organisation;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.service.ContractService;
import nl.hielkefellinger.etl_app.service.OrganisationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncContractingPipeline extends EtlPipeline<Contract, Contract> {

    private final ContractService contractService;
    private final OrganisationService organisationService;

    protected static final String NAME_CONTRACT_HQ = "Contract HQ";
    protected static final String NAME_CONTRACT_C1 = "Contract Customer-1";
    protected static final String NAME_CONTRACT_C2 = "Contract Customer-2";
    protected static final String NAME_ORG_HQ = "HQ";
    protected static final String NAME_ORG_C1 = "Customer-1";
    protected static final String NAME_ORG_C2 = "Customer-2";

    @Override
    protected List<Contract> extract() {
        var rawContracts = new ArrayList<Contract>();

        rawContracts.add(createContract(NAME_CONTRACT_HQ, 1));
        rawContracts.add(createContract(NAME_CONTRACT_C1, 2));
        rawContracts.add(createContract(NAME_CONTRACT_C2, 3));
        return rawContracts;
    }

    @Override
    protected List<Contract> transform(List<Contract> input) {
        var transformedContracts = new ArrayList<Contract>();
        for (var contract : input) {
            if (contract.getName().equals(NAME_CONTRACT_HQ)) {
                contract.setOrganisation(createOrgs(NAME_ORG_HQ));
            } else if (contract.getName().equals(NAME_CONTRACT_C1)) {
                contract.setOrganisation(createOrgs(NAME_ORG_C1));
            } else if (contract.getName().equals(NAME_CONTRACT_C2)) {
                contract.setOrganisation(createOrgs(NAME_ORG_C2));
            }
            transformedContracts.add(contract);
        }
        return transformedContracts;
    }

    @Override
    protected List<Contract> load(List<Contract> output) {
        var loadedContracts = new ArrayList<Contract>();
        for (var contract : output) {
            // Add Org if needed; an added Contract always has an org
            if (organisationService.getOrganisationByName(contract.getOrganisation().getName()) == null) {
                contract.setOrganisation(organisationService.createNewOrganisation(contract.getOrganisation()));
            }

            // Add Contract if needed
            if (contractService.getContractByName(contract.getName()) == null) {
                loadedContracts.add(contractService.createNewContract(contract));
            }
        }
        return loadedContracts;
    }

    private static Organisation createOrgs(String name) {
        var org = new Organisation();
        org.setName(name);
        return org;
    }

    private static Contract createContract(String name, int days) {
        var crt = new Contract();
        crt.setName(name);
        crt.setStartTime(LocalDateTime.now().minusDays(days));
        return crt;
    }
}
