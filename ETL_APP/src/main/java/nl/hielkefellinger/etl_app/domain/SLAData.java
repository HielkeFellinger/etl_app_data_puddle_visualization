package nl.hielkefellinger.etl_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "sla_data")
@Entity
@Data
@NoArgsConstructor
@IdClass(SLAData.SLADataKey.class)
public class SLAData {

    @Data
    public static class SLADataKey implements Serializable {
        private UUID id;
        private LocalDateTime time;
        private UUID sla;
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
    @JoinColumn(name = "sla_id", nullable = false)
    private SLA sla;
}
