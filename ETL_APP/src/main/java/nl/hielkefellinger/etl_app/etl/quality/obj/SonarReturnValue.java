package nl.hielkefellinger.etl_app.etl.quality.obj;

import lombok.Data;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;

import java.math.BigDecimal;

@Data
public class SonarReturnValue {
    private ServiceInstance serviceInstance;
    private SonarMetric sonarMetric;
    private BigDecimal value;

    public SonarReturnValue(ServiceInstance serviceInstance, SonarMetric sonarMetric) {
        this.serviceInstance = serviceInstance;
        this.sonarMetric = sonarMetric;
    }

    public boolean hasRequiredData() {
        return serviceInstance != null && sonarMetric != null && value != null;
    }
}
