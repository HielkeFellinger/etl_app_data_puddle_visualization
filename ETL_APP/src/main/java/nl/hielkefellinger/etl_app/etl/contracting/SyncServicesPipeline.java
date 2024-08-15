package nl.hielkefellinger.etl_app.etl.contracting;

import lombok.RequiredArgsConstructor;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import nl.hielkefellinger.etl_app.enums.ServiceType;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.service.ContractService;
import nl.hielkefellinger.etl_app.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncServicesPipeline extends EtlPipeline<ServiceInstance, ServiceInstance> {

    private final ContractService contractService;
    private final ServiceInstanceService serviceInstanceService;

    protected static final String NAME_SERV_Q_1 = "Service Q1 APP";
    protected static final String NAME_SERV_K1_1 = "Service K1 APP - FE";
    protected static final String NAME_SERV_K1_2 = "Service K1 APP - FE - SUPPORT";
    protected static final String NAME_SERV_K1_3 = "Service K1 APP - BE";
    protected static final String NAME_SERV_K1_4 = "Service K1 APP - BE - SUPPORT";
    protected static final String NAME_SERV_K2_1 = "Service K2 APP - FE";
    protected static final String NAME_SERV_K2_2 = "Service K2 APP - FE - SUPPORT";

    @Override
    protected List<ServiceInstance> extract() {
        var rawServices = new ArrayList<ServiceInstance>();
        rawServices.add(getServiceInstance(NAME_SERV_Q_1, ServiceType.HOSTING, "Hosting Q1 APP"));
        rawServices.add(getServiceInstance(NAME_SERV_K1_1, ServiceType.HOSTING, "Hosting K1 APP - FE"));
        rawServices.add(getServiceInstance(NAME_SERV_K1_2, ServiceType.SUPPORT, "Support K1 APP - FE"));
        rawServices.add(getServiceInstance(NAME_SERV_K1_3, ServiceType.HOSTING, "Hosting K1 APP - BE"));
        rawServices.add(getServiceInstance(NAME_SERV_K1_4, ServiceType.SUPPORT, "Support K1 APP - BE"));
        rawServices.add(getServiceInstance(NAME_SERV_K2_1, ServiceType.HOSTING, "Hosting K2 APP - FE"));
        rawServices.add(getServiceInstance(NAME_SERV_K2_2, ServiceType.SUPPORT, "Support K2 APP - FE"));
        return rawServices;
    }

    @Override
    protected List<ServiceInstance> transform(List<ServiceInstance> input) {
        var services = new ArrayList<ServiceInstance>();
        for (var service : input) {
            addContractToRightService(service);
            services.add(service);
        }
        return services;
    }

    @Override
    protected List<ServiceInstance> load(List<ServiceInstance> output) {
        var addedServices = new ArrayList<ServiceInstance>();
        for (var service : output) {
            // Add Service if needed
            if (service.getContract() != null && service.getContract().getId() != null) {
                if (serviceInstanceService.getServiceByName(service.getName()) == null) {
                    addedServices.add(serviceInstanceService.createService(service));
                }
            } else {
                logger.warn("Service '{}' has NO valid Contract", service.getName());
            }
        }
        return addedServices;
    }

    private void addContractToRightService(ServiceInstance service) {
        if (service.getName().equals(NAME_SERV_Q_1)) {
            service.setContract(contractService.getContractByName(SyncContractingPipeline.NAME_CONTRACT_HQ));
        } else if (service.getName().equals(NAME_SERV_K1_1) || service.getName().equals(NAME_SERV_K1_2) ||
            service.getName().equals(NAME_SERV_K1_3) || service.getName().equals(NAME_SERV_K1_4)) {
            service.setContract(contractService.getContractByName(SyncContractingPipeline.NAME_CONTRACT_C1));
        } else if (service.getName().equals(NAME_SERV_K2_1) || service.getName().equals(NAME_SERV_K2_2)) {
            service.setContract(contractService.getContractByName(SyncContractingPipeline.NAME_CONTRACT_C2));
        }
    }

    private static ServiceInstance getServiceInstance(String name, ServiceType type, String description) {
        var servQ = new ServiceInstance();
        servQ.setName(name);
        servQ.setServiceType(type);
        servQ.setDescription(description);
        return servQ;
    }
}
