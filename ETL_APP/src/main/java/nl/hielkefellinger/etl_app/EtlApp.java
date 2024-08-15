package nl.hielkefellinger.etl_app;

import nl.hielkefellinger.etl_app.config.EtlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(EtlConfig.class)
public class EtlApp {

    private static final Logger log = LoggerFactory.getLogger(EtlApp.class);

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication app = new SpringApplication(EtlApp.class);
        app.run(args);
    }
}
