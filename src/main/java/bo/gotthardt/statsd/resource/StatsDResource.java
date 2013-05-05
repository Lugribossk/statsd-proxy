package bo.gotthardt.statsd.resource;

import bo.gotthardt.statsd.StatsDMetricType;
import com.google.common.base.Optional;
import com.timgroup.statsd.StatsDClient;
import com.yammer.metrics.annotation.Metered;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    private final String gif;

    @GET
    @Metered
    public Response one(@QueryParam("p") String path,
                        @QueryParam("t") Optional<StatsDMetricType> type,
                        @QueryParam("v") Optional<Integer> value) {
        if (path != null) {
            type.or(StatsDMetricType.COUNTER).record(statsd, path, value);
        }

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Metered
    @Path("/s.gif")
    @Produces("image/gif")
    public String gif(@QueryParam("p") String path,
                      @QueryParam("t") Optional< StatsDMetricType > type,
                      @QueryParam("v") Optional<Integer> value) {
        if (path != null) {
            type.or(StatsDMetricType.COUNTER).record(statsd, path, value);
        }

        return gif;
    }
}
