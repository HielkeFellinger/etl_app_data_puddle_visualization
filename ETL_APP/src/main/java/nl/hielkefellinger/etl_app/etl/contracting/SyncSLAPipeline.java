package nl.hielkefellinger.etl_app.etl.contracting;

import lombok.RequiredArgsConstructor;
import nl.hielkefellinger.etl_app.domain.KPI;
import nl.hielkefellinger.etl_app.etl.EtlPipeline;
import nl.hielkefellinger.etl_app.repository.SLARepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncSLAPipeline extends EtlPipeline<KPI, KPI> {

    private final SLARepository slaRepository;

    @Override
    protected List<KPI> extract() {
        return List.of();
    }

    @Override
    protected List<KPI> transform(List<KPI> input) {
        return List.of();
    }

    @Override
    protected List<KPI> load(List<KPI> output) {
        return List.of();
    }
}
