package bo.gotthardt.statsd.resource;

import bo.gotthardt.util.ImprovedResourceTest;
import com.timgroup.statsd.StatsDClient;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static bo.gotthardt.util.fest.DropwizardAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link StatsDResource}.
 *
 * @author Bo Gotthardt
 */
public class StatsDResourceTest extends ImprovedResourceTest {
    private StatsDClient statsd = mock(StatsDClient.class);

    @Override
    protected void setUpResources() throws Exception {
        addResource(new StatsDResource(statsd));
    }

    @Test
    public void counterWorks() {
        assertThat(GET("/stats?b=testbucket&m=counter&v=2"))
                .hasStatus(Response.Status.NO_CONTENT);

        verify(statsd).count("testbucket", 2);
    }

    @Test
    public void gaugeWorks() {
        assertThat(GET("/stats?b=testbucket&m=gauge&v=5"))
                .hasStatus(Response.Status.NO_CONTENT);

        verify(statsd).gauge("testbucket", 5);
    }

    @Test
    public void timeWorks() {
        assertThat(GET("/stats?b=testbucket&m=time&v=1000"))
                .hasStatus(Response.Status.NO_CONTENT);

        verify(statsd).time("testbucket", 1000);
    }

    @Test
    public void counterDefaultsValueTo1() {
        assertThat(GET("/stats?b=testbucket&m=counter"))
                .hasStatus(Response.Status.NO_CONTENT);

        verify(statsd).count("testbucket", 1);
    }

    @Test
    public void metricDefaultsToCounter() {
        assertThat(GET("/stats?b=testbucket"))
                .hasStatus(Response.Status.NO_CONTENT);

        verify(statsd).count("testbucket", 1);
    }

    @Test
    public void invalidRequestDoesNotBlowUp() {
        assertThat(GET("/stats"))
                .hasStatus(Response.Status.NO_CONTENT);
    }
}