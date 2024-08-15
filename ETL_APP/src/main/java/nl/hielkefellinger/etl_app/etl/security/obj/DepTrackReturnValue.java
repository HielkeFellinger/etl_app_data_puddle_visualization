package nl.hielkefellinger.etl_app.etl.security.obj;

import lombok.Data;
import nl.hielkefellinger.etl_app.domain.ServiceInstance;

import java.math.BigDecimal;

@Data
public class DepTrackReturnValue {
    private ServiceInstance service;
    private BigDecimal value;
}
