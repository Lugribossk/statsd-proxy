package bo.gotthardt.statsd.configuration;


import com.yammer.dropwizard.config.Configuration;
import lombok.Getter;

import javax.validation.Valid;

/**
 * Configuration for {@link bo.gotthardt.statsd.application.StatsDProxyApplication}.
 *
 * @author Bo Gotthardt
 */
@Getter
public class StatsDProxyConfiguration extends Configuration {
    @Valid
    private StatsDConfiguration statsd = new StatsDConfiguration();
}
