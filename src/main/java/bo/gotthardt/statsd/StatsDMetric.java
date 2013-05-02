package bo.gotthardt.statsd;

import com.google.common.base.Optional;
import com.timgroup.statsd.StatsDClient;
import lombok.RequiredArgsConstructor;

/**
 * The types of metrics StatsD/Graphite uses.
 *
 * @author Bo Gotthardt
 */
@RequiredArgsConstructor
public enum StatsDMetric {
    COUNTER("c") {
        @Override
        public void record(StatsDClient statsd, String bucket, Optional<Integer> value) {
            statsd.count(bucket, value.or(1));
        }
    },
    GAUGE("g") {
        @Override
        public void record(StatsDClient statsd, String bucket, Optional<Integer> value) {
            if (value.isPresent()) {
                statsd.gauge(bucket, value.get());
            }
        }
    },
    TIME("t") {
        @Override
        public void record(StatsDClient statsd, String bucket, Optional<Integer> value) {
            if (value.isPresent()) {
                statsd.time(bucket, value.get());
            }
        }
    };

    private final String externalForm;

    public abstract void record(StatsDClient statsd, String bucket, Optional<Integer> value);

    public static StatsDMetric fromString(String input) {
        for (StatsDMetric action : values()) {
            if (action.name().equalsIgnoreCase(input) || action.externalForm.equalsIgnoreCase(input)) {
                return action;
            }
        }
        return null;
    }
}
