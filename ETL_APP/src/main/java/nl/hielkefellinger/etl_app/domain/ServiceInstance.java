package nl.hielkefellinger.etl_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hielkefellinger.etl_app.enums.ServiceType;

import java.util.UUID;

@Table(name = "service")
@Entity
@Data
@NoArgsConstructor
public class ServiceInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @OneToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
}
