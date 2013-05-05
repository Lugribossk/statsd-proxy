package bo.gotthardt.statsd;

import lombok.RequiredArgsConstructor;

import com.google.common.base.Optional;
import com.timgroup.statsd.StatsDClient;

/**
 * The types of metrics StatsD/Graphite uses.
 *
 * @author Bo Gotthardt
 */
@RequiredArgsConstructor
public enum StatsDMetricType {
    COUNTER("c") {
        @Override
        public void record(StatsDClient statsd, String path, Optional<Integer> value) {
            statsd.count(path, value.or(1));
        }
    },
    GAUGE("g") {
        @Override
        public void record(StatsDClient statsd, String path, Optional<Integer> value) {
            if (value.isPresent()) {
                statsd.gauge(path, value.get());
            }
        }
    },
    TIME("t") {
        @Override
        public void record(StatsDClient statsd, String path, Optional<Integer> value) {
            if (value.isPresent()) {
                statsd.time(path, value.get());
            }
        }
    };

    private final String externalForm;

    public abstract void record(StatsDClient statsd, String path, Optional<Integer> value);

    public static StatsDMetricType fromString(String input) {
        for (StatsDMetricType action : values()) {
            if (action.name().equalsIgnoreCase(input) || action.externalForm.equalsIgnoreCase(input)) {
                return action;
            }
        }
        return null;
    }
}
