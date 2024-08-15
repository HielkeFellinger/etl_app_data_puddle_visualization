package nl.hielkefellinger.etl_app.etl.sensors;

import lombok.RequiredArgsConstructor;
import nl.hielkefellinger.etl_app.config.EtlConfig;
import nl.hielkefellinger.etl_app.domain.Sensor;
import nl.hielkefellinger.etl_app.enums.SensorGroup;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.service.SensorDataService;
import nl.hielkefellinger.etl_app.service.SensorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncSensorPipeline extends EtlPipeline<String, Sensor> {

    private final EtlConfig etlConfig;
    private final SensorDataService sensorDataService;
    private final SensorService sensorService;

    @Override
    protected List<String> extract() {
        return etlConfig.getSensors();
    }

    @Override
    protected List<Sensor> transform(List<String> input) {
        var sensors = new ArrayList<Sensor>();
        for (String rawSensor : input) {
            var parts = rawSensor.split(",");
            if (parts.length == 4) {
                var sensor = new Sensor();
                sensor.setName(parts[0]);
                sensor.setDescription(parts[1]);
                sensor.setUnit(parts[2]);
                sensor.setSensorGroup(SensorGroup.getSensorGroup(parts[3]));
                sensors.add(sensor);
            } else {
                logger.error("Raw Sensor: '{}' has to few properties", rawSensor);
            }
        }
        return sensors;
    }

    @Override
    protected List<Sensor> load(List<Sensor> output) {
        var addedSensors = new ArrayList<Sensor>();
        for (var sensor : output) {
            if (sensorService.getSensorByName(sensor.getName()) == null) {
                addedSensors.add(sensorService.createSensor(sensor));
            }
        }
        return addedSensors;
    }
}
