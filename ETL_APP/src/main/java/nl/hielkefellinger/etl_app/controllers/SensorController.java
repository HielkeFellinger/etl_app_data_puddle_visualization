package nl.hielkefellinger.etl_app.controllers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.etl.sensors.SyncSensorPipeline;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SensorController {

    private final SyncSensorPipeline syncSensorPipeline;

    @PostConstruct
    private void init() {
        try {
            log.info("Sync Sensors Pipeline");
            syncSensorPipeline.run();
            log.info("Done Syncing of Sensors Pipeline");
        } catch (RuntimeException e) {
            log.error("Failed running Sync. of Sensors. Aborting", e);
            throw new Error(e);
        }
    }
}
