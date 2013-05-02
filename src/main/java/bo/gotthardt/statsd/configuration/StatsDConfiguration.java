package bo.gotthardt.statsd.configuration;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Bo Gotthardt
 */
@Getter
public class StatsDConfiguration {
    @NotEmpty
    private String prefix;
    @NotEmpty
    private String host;
    private int port = 8125;
}
