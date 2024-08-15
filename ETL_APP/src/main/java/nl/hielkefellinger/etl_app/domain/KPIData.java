package nl.hielkefellinger.etl_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "kpi_data")
@Entity
@Data
@NoArgsConstructor
@IdClass(KPIData.KPIDataKey.class)
public class KPIData {

    @Data
    public static class KPIDataKey implements Serializable {
        private UUID id;
        private LocalDateTime time;
        private UUID kpi;
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
    @JoinColumn(name = "kpi_id",nullable = false)
    private KPI kpi;
}
