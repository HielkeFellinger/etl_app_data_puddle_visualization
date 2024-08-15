package nl.hielkefellinger.etl_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "sensor_data")
@Entity
@Data
@NoArgsConstructor
@IdClass(SensorData.SensorDataKey.class)
public class SensorData {

    @Data
    public static class SensorDataKey implements Serializable {
        private UUID id;
        private LocalDateTime time;
        private String sensor;
        private UUID service;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Id
    @Column(nullable = false)
    private LocalDateTime time;
    private BigDecimal value;

    // relations
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_name",nullable = false)
    private Sensor sensor;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceInstance service;
}
