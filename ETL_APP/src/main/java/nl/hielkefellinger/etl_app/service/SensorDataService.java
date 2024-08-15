package nl.hielkefellinger.etl_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.SensorData;
import nl.hielkefellinger.etl_app.repository.SensorDataRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public SensorData createNewSensorData(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }
}
