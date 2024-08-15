package nl.hielkefellinger.etl_app.etl.quality;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.domain.SensorData;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;
import nl.hielkefellinger.etl_app.enums.ServiceType;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.etl.quality.obj.SonarMetric;
import nl.hielkefellinger.etl_app.etl.quality.obj.SonarReturnValue;
import nl.hielkefellinger.etl_app.service.SensorDataService;
import nl.hielkefellinger.etl_app.service.SensorService;
import nl.hielkefellinger.etl_app.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SonarEtlPipeline extends EtlPipeline<SonarReturnValue, SensorData> {

    private final SensorService sensorService;
    private final ServiceInstanceService serviceInstanceService;
    private final SensorDataService sensorDataService;

    private Sensor sonarCoverageSensor;
    private Sensor sonarScoreSensor;
    private Sensor sonarLocSensor;
    private Map<SonarMetric, Sensor> sonarMetricMap;

    @Override
    protected List<SonarReturnValue> extract() {
        loadBufferedSensors();
        var sonarReturnValues = new ArrayList<SonarReturnValue>();
        var activeHostedServices = serviceInstanceService.getActiveServices().stream()
            .filter(s -> s.getServiceType() == ServiceType.HOSTING)
            .toList();

        for (ServiceInstance service : activeHostedServices) {
            var coverage = new SonarReturnValue(service, SonarMetric.COVERAGE);
            coverage.setValue(BigDecimal.valueOf(60.0 + (Math.random() * 40)).setScale(2, RoundingMode.HALF_UP));
            var score = new SonarReturnValue(service, SonarMetric.SCORE);
            score.setValue(BigDecimal.valueOf(6 + (Math.random() * 4)).setScale(1, RoundingMode.HALF_UP));
            var loc = new SonarReturnValue(service, SonarMetric.LOC);
            loc.setValue(BigDecimal.valueOf(1000  + (Math.random() * 5000)).setScale(0, RoundingMode.HALF_UP));

            if (coverage.hasRequiredData()) sonarReturnValues.add(coverage);
            if (score.hasRequiredData()) sonarReturnValues.add(score);
            if (loc.hasRequiredData()) sonarReturnValues.add(loc);
        }

        return sonarReturnValues;
    }

    @Override
    protected List<SensorData> transform(List<SonarReturnValue> input) {
        var newSensorData = new ArrayList<SensorData>();
        for (SonarReturnValue sonarReturnValue : input) {
            var sensorData = new SensorData();
            sensorData.setTime(LocalDateTime.now());
            sensorData.setValue(sonarReturnValue.getValue());
            sensorData.setService(sonarReturnValue.getServiceInstance());
            sensorData.setSensor(sonarMetricMap.get(sonarReturnValue.getSonarMetric()));
            newSensorData.add(sensorData);
        }
        return newSensorData;
    }

    @Override
    protected List<SensorData> load(List<SensorData> output) {
        return output.stream().map(sensorDataService::createNewSensorData).toList();
    }

    private void loadBufferedSensors() {
        // Load and buffer sensors if needed
        if (sonarLocSensor == null || sonarCoverageSensor == null || sonarScoreSensor == null) {
            sonarCoverageSensor = sensorService.getSensorByName("SONAR_COVERAGE");
            sonarScoreSensor = sensorService.getSensorByName("SONAR_SCORE");
            sonarLocSensor = sensorService.getSensorByName("SONAR_LOC");

            sonarMetricMap = new EnumMap<>(SonarMetric.class);
            sonarMetricMap.put(SonarMetric.COVERAGE, sonarCoverageSensor);
            sonarMetricMap.put(SonarMetric.SCORE, sonarScoreSensor);
            sonarMetricMap.put(SonarMetric.LOC, sonarLocSensor);

            if (sonarLocSensor == null || sonarCoverageSensor == null || sonarScoreSensor == null) {
                throw new RuntimeException("sonar coverage and/or score sensor are null");
            }
        }
    }
}
