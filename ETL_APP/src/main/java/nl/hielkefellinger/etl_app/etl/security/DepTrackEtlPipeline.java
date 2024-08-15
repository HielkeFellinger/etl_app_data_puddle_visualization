package nl.hielkefellinger.etl_app.etl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.domain.SensorData;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import nl.hielkefellinger.etl_app.enums.ServiceType;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.etl.security.obj.DepTrackReturnValue;
import nl.hielkefellinger.etl_app.service.SensorDataService;
import nl.hielkefellinger.etl_app.service.SensorService;
import nl.hielkefellinger.etl_app.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepTrackEtlPipeline extends EtlPipeline<DepTrackReturnValue, SensorData> {

    private final SensorService sensorService;
    private final ServiceInstanceService serviceInstanceService;
    private final SensorDataService sensorDataService;

    private Sensor sensorRiscScore;

    @Override
    protected List<DepTrackReturnValue> extract() {
        loadBufferedSensors();
        var depTrackResults = new ArrayList<DepTrackReturnValue>();
        var activeHostedServices = serviceInstanceService.getActiveServices().stream()
            .filter(s -> s.getServiceType() == ServiceType.HOSTING)
            .toList();

        for (ServiceInstance service : activeHostedServices) {
            var result = new DepTrackReturnValue();
            result.setService(service);
            result.setValue(BigDecimal.valueOf(5 + (Math.random() * 5)).setScale(0, RoundingMode.HALF_UP));
            depTrackResults.add(result);
        }

        return depTrackResults;
    }

    @Override
    protected List<SensorData> transform(List<DepTrackReturnValue> input) {
        var sensorData = new ArrayList<SensorData>();
        for (DepTrackReturnValue depTrackReturnValue : input) {
            var data = new SensorData();
            data.setSensor(this.sensorRiscScore);
            data.setValue(depTrackReturnValue.getValue());
            data.setTime(LocalDateTime.now());
            data.setService(depTrackReturnValue.getService());
            sensorData.add(data);
        }
        return sensorData;
    }

    @Override
    protected List<SensorData> load(List<SensorData> output) {
        return output.stream().map(sensorDataService::createNewSensorData).toList();
    }

    private void loadBufferedSensors() {
        // Load and buffer sensors if needed
        if (sensorRiscScore == null) {
            sensorRiscScore = sensorService.getSensorByName("RISC_SCORE");
            if (sensorRiscScore == null) {
                throw new RuntimeException("DepTrack Risc Score sensor are null");
            }
        }
    }
}
