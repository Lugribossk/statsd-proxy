package bo.gotthardt.statsd.application;

import bo.gotthardt.statsd.configuration.StatsDConfiguration;
import bo.gotthardt.statsd.configuration.StatsDProxyConfiguration;
import bo.gotthardt.statsd.jersey.filter.AllowAllOriginsFilter;
import bo.gotthardt.statsd.resource.StatsDResource;
import com.datasift.dropwizard.bundles.GraphiteReportingBundle;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientErrorHandler;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple application for proxying HTTP requests to a StatsD server.
 * This is useful for sending data directly from a Javascript application.
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
        bootstrap.addBundle(new GraphiteReportingBundle());
    }

    @Override
    public void run(StatsDProxyConfiguration configuration, Environment environment) throws Exception {
        environment.addHealthCheck(new StatsDHealthCheck(configuration.getStatsd()));

        String gif = Resources.toString(Resources.getResource("1x1.gif"), Charsets.UTF_8);
        environment.addResource(new StatsDResource(createStatsDClient(configuration.getStatsd()), gif));

        // Allow all origins on all requests.
        environment.addFilter(AllowAllOriginsFilter.class, "*");
    }

    private static StatsDClient createStatsDClient(StatsDConfiguration config) {
        StatsDClientErrorHandler errorHandler = new StatsDClientErrorHandler() {
            @Override
            public void handle(Exception exception) {
                log.warn("StatsD error:", exception);
            }
        };

        return new NonBlockingStatsDClient(config.getPrefix(), config.getHost(), config.getPort(), errorHandler);
    }
}
