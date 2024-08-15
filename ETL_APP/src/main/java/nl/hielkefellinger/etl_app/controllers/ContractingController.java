package nl.hielkefellinger.etl_app.controllers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.hielkefellinger.etl_app.etl.contracting.SyncContractingPipeline;
import nl.hielkefellinger.etl_app.etl.contracting.SyncServicesPipeline;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContractingController {

    private final SyncContractingPipeline contractingPipeline;
    private final SyncServicesPipeline syncServicesPipeline;

    @PostConstruct
    @Scheduled(fixedRate = 25000)
    private void syncContractStructure() {
        try {
            log.info("Sync Contracting Pipelines");
            contractingPipeline.run();
            syncServicesPipeline.run();
            log.info("Done Syncing of Contracting Pipelines");
        } catch (RuntimeException e) {
            log.error("Failed running Sync. of Contract Structure.", e);
        }
    }

}
