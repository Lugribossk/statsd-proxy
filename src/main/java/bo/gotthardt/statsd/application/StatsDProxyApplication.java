package bo.gotthardt.statsd.application;

import bo.gotthardt.statsd.configuration.StatsDConfiguration;
import bo.gotthardt.statsd.configuration.StatsDProxyConfiguration;
import bo.gotthardt.statsd.resource.StatsDResource;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientErrorHandler;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple application for proxying HTTP requests to a StatsD server.
 *
 * @author Bo Gotthardt
 */
@Slf4j
public class StatsDProxyApplication extends Service<StatsDProxyConfiguration> {

    public static void main(String... args) throws Exception {
        new StatsDProxyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<StatsDProxyConfiguration> bootstrap) {
        // Empty on purpose.
    }

    @Override
    public void run(StatsDProxyConfiguration configuration, Environment environment) throws Exception {
        environment.addHealthCheck(new StatsDHealthCheck(configuration.getStatsd()));

        environment.addResource(new StatsDResource(createStatsDClient(configuration.getStatsd())));
    }

    private static StatsDClient createStatsDClient(StatsDConfiguration config) {
        StatsDClientErrorHandler errorHandler = new StatsDClientErrorHandler() {
            @Override
            public void handle(Exception exception) {
                log.warn("", exception);
            }
        };

        return  new NonBlockingStatsDClient(config.getPrefix(), config.getHost(), config.getPort(), errorHandler);
    }
}
