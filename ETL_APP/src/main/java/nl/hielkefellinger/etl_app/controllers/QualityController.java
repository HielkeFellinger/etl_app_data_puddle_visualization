package nl.hielkefellinger.etl_app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.etl.quality.SonarEtlPipeline;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QualityController {

    private final SonarEtlPipeline sonarEtlPipeline;

    @Scheduled(fixedRate = 25000)
    private void runSonarPipelines() {
        try {
            log.info("Running Quality-Sonar Pipeline(s)");
            sonarEtlPipeline.run();
            log.info("Done Quality-Sonar Pipeline(s)");
        } catch (RuntimeException e) {
            log.error("Failed running Quality-Sonar Pipeline(s)", e);
        }
    }
}
