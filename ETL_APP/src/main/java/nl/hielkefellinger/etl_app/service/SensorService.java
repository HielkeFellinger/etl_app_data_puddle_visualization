package nl.hielkefellinger.etl_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.enums.SensorGroup;
import nl.hielkefellinger.etl_app.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public Sensor getSensorByName(String sensorName) {
        return sensorRepository.findByName(sensorName);
    }

    public Sensor getSensorById(UUID sensorId) {
        return sensorRepository.getReferenceById(sensorId);
    }

    public List<Sensor> getAllSensorsOfGroup(SensorGroup sensorGroup) {
        return sensorRepository.findAllBySensorGroup(sensorGroup);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
}
