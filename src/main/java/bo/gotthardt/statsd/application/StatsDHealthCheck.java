package bo.gotthardt.statsd.application;

import bo.gotthardt.statsd.configuration.StatsDConfiguration;
import com.datasift.dropwizard.health.SocketHealthCheck;

import java.net.Socket;

/**
 * Health check for StatsD connection.
 *
 * @author Bo Gotthardt
 */
public class StatsDHealthCheck extends SocketHealthCheck {

    public StatsDHealthCheck(StatsDConfiguration config) {
        super(config.getHost(), config.getPort(), "statsd");
    }

    @Override
    protected Result check(Socket socket) {
        return Result.healthy();
    }
}
