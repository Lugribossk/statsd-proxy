package bo.gotthardt.statsd.configuration;


import com.datasift.dropwizard.config.GraphiteConfiguration;
import com.datasift.dropwizard.config.GraphiteReportingConfiguration;
import com.yammer.dropwizard.config.Configuration;
import lombok.Getter;

import javax.validation.Valid;

/**
 * Configuration for {@link bo.gotthardt.statsd.application.StatsDProxyApplication}.
 *
 * @author Bo Gotthardt
 */
@Getter
public class StatsDProxyConfiguration extends Configuration implements GraphiteReportingConfiguration {
    @Valid
    private StatsDConfiguration statsd = new StatsDConfiguration();
    @Valid
    private GraphiteConfiguration graphite = new GraphiteConfiguration();
}
