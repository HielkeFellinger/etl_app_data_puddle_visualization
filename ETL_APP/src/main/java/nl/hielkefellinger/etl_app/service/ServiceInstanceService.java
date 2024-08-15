package nl.hielkefellinger.etl_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import nl.hielkefellinger.etl_app.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceInstanceService {

    private final ServiceRepository serviceRepository;

    public List<ServiceInstance> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceInstance getServiceById(UUID serviceId) {
        return serviceRepository.findById(serviceId).orElse(null);
    }

    public List<ServiceInstance> getActiveServices() {
        return serviceRepository.findAllActiveServices();
    }

    public ServiceInstance getServiceByName(String name) {
        return serviceRepository.findByName(name).orElse(null);
    }

    public ServiceInstance createService(ServiceInstance serviceInstance) {
        return serviceRepository.save(serviceInstance);
    }
}
