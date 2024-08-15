package nl.hielkefellinger.etl_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import nl.hielkefellinger.etl_app.enums.SensorGroup;

@Table(name = "sensor")
@Entity
@Data
public class Sensor {

    @Id
    @Column(nullable = false)
    private String name;
    private String description;
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensor_group", nullable = false)
    private SensorGroup sensorGroup;
}
