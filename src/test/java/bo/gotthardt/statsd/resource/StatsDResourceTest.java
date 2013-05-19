package bo.gotthardt.statsd.resource;

import bo.gotthardt.util.ImprovedResourceTest;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.timgroup.statsd.StatsDClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
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
        addResource(new StatsDResource(statsd, ""));
    }

    @Test
    public void counterWorks() {
        assertThat(GET("/stats?p=testbucket&t=counter&v=2"))
                .hasStatus(Response.Status.OK);

        verify(statsd).count("testbucket", 2);
    }

    @Test
    public void gaugeWorks() {
        assertThat(GET("/stats?p=testbucket&t=gauge&v=5"))
                .hasStatus(Response.Status.OK);

        verify(statsd).gauge("testbucket", 5);
    }

    @Test
    public void timeWorks() {
        assertThat(GET("/stats?p=testbucket&t=time&v=1000"))
                .hasStatus(Response.Status.OK);

        verify(statsd).time("testbucket", 1000);
    }

    @Test
    public void counterDefaultsValueTo1() {
        assertThat(GET("/stats?p=testbucket&t=counter"))
                .hasStatus(Response.Status.OK);

        verify(statsd).count("testbucket", 1);
    }

    @Test
    public void metricDefaultsToCounter() {
        assertThat(GET("/stats?p=testbucket"))
                .hasStatus(Response.Status.OK);

        verify(statsd).count("testbucket", 1);
    }

    @Test
    public void invalidRequestDoesNotBlowUp() {
        assertThat(GET("/stats"))
                .hasStatus(Response.Status.OK);
    }

    @Test
    public void postWorks() {
        MultivaluedMap<String, String> values = new MultivaluedMapImpl();
        values.add("p", "testbucket");
        values.add("t", "counter");
        values.add("v", "2");

        assertThat(POST("/stats?p=testbucket&t=counter&v=2", values, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
                .hasStatus(Response.Status.OK);

        verify(statsd).count("testbucket", 2);
    }
}
