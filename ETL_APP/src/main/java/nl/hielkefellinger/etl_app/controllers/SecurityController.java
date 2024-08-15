package nl.hielkefellinger.etl_app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.etl.security.ApmEtlPipeline;
import nl.hielkefellinger.etl_app.etl.security.DepTrackEtlPipeline;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SecurityController {

    private final ApmEtlPipeline apmEtlPipeline;
    private final DepTrackEtlPipeline depTrackEtlPipeline;

    @Scheduled(fixedRate = 25000)
    private void runDepTrackPipelines() {
        try {
            log.info("Running Security-Dep.Track Pipeline(s)");
            depTrackEtlPipeline.run();
            log.info("Done Security-Dep.Track Pipeline(s)");
        } catch (RuntimeException e) {
            log.error("Failed running Security-Dep.Track Pipeline(s)", e);
        }
    }

    @Scheduled(fixedRate = 25000)
    private void runApmPipelines() {
        try {
            log.info("Running Security-APM Pipeline(s)");
            apmEtlPipeline.run();
            log.info("Done Security-APM Pipeline(s)");
        } catch (RuntimeException e) {
            log.error("Failed running Security-APM Pipeline(s)", e);
        }
    }
}
