package nl.hielkefellinger.etl_app.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class EtlPipeline<E, L> {

    protected final Semaphore semaphore = new Semaphore(1);
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() throws RuntimeException {
        try {
            semaphore.acquire();
            try {
                logger.info("-> Extract");
                var extract = this.extract();
                logger.info("-> Transform  '{}' items", extract.size());
                var transform = this.transform(extract);
                logger.info("-> Load '{}' items", transform.size());
                var loaded = this.load(transform);
                logger.info("= Changes '{}' items", loaded.size());
            } finally {
                logger.info("Done");
                semaphore.release();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected abstract List<E> extract();
    protected abstract List<L> transform(List<E> input);
    protected abstract List<L> load(List<L> output);
}
