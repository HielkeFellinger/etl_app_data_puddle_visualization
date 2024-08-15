package nl.hielkefellinger.etl_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Setter
@ConfigurationProperties(prefix = "etl")
@Getter
public class EtlConfig {
    private List<String> sensors;
}
