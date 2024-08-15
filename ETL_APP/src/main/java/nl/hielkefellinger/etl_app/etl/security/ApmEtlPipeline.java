package nl.hielkefellinger.etl_app.etl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.domain.SensorData;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import nl.hielkefellinger.etl_app.enums.ServiceType;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.etl.security.obj.ApmReturnValue;
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
public class ApmEtlPipeline extends EtlPipeline<ApmReturnValue, SensorData> {

    private final SensorService sensorService;
    private final ServiceInstanceService serviceInstanceService;
    private final SensorDataService sensorDataService;

    private Sensor sensorUptimeScore;

    @Override
    protected List<ApmReturnValue> extract() {
        loadBufferedSensors();
        var depTrackResults = new ArrayList<ApmReturnValue>();
        var activeHostedServices = serviceInstanceService.getActiveServices().stream()
            .filter(s -> s.getServiceType() == ServiceType.HOSTING)
            .toList();

        for (ServiceInstance service : activeHostedServices) {
            var result = new ApmReturnValue();
            result.setService(service);
            result.setDayValue(BigDecimal.valueOf(5 + (Math.random() * 20)).setScale(3, RoundingMode.HALF_UP));
            depTrackResults.add(result);
        }

        return depTrackResults;
    }

    @Override
    protected List<SensorData> transform(List<ApmReturnValue> input) {
        var sensorData = new ArrayList<SensorData>();
        for (ApmReturnValue returnValue : input) {
            var data = new SensorData();
            data.setSensor(this.sensorUptimeScore);
            data.setValue(returnValue.getDayValue());
            data.setTime(LocalDateTime.now());
            data.setService(returnValue.getService());
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
        if (sensorUptimeScore == null) {
            sensorUptimeScore = sensorService.getSensorByName("UPTIME");
            if (sensorUptimeScore == null) {
                throw new RuntimeException("APM UPTIME Score sensor are null");
            }
        }
    }
}
