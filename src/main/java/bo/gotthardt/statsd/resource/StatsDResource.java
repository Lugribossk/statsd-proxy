package bo.gotthardt.statsd.resource;

import bo.gotthardt.statsd.StatsDMetric;
import com.google.common.base.Optional;
import com.timgroup.statsd.StatsDClient;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Resource for accepting StatsD events.
 *
 * @author Bo Gotthardt
 */
@Path("/stats")
@RequiredArgsConstructor
public class StatsDResource {
    private final StatsDClient statsd;

    @GET
    public Response sendStats(@QueryParam("b") String bucket,
                              @QueryParam("m") Optional<StatsDMetric> metric,
                              @QueryParam("v") Optional<Integer> value) {
        if (bucket != null) {
            metric.or(StatsDMetric.COUNTER).record(statsd, bucket, value);
        }

        // Return "no content" so TODO
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
