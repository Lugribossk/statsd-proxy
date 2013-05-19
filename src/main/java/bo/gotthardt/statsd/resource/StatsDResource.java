package bo.gotthardt.statsd.resource;

import bo.gotthardt.statsd.StatsDMetricType;
import com.google.common.base.Optional;
import com.timgroup.statsd.StatsDClient;
import com.yammer.metrics.annotation.Metered;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public Response get(@QueryParam("p") String path,
                        @QueryParam("t") Optional<StatsDMetricType> type,
                        @QueryParam("v") Optional<Integer> value) {
        record(path, type, value);
        return Response.ok().build();
    }

    @POST
    @Metered
    public Response post(@FormParam("p") String path,
                         @FormParam("t") StatsDMetricType type,
                         @FormParam("v") Integer value) {
        record(path, Optional.of(type), Optional.of(value));
        return Response.ok().build();
    }

    @GET
    @Metered
    @Path("/s.gif")
    @Produces("image/gif")
    public String gif(@QueryParam("p") String path,
                      @QueryParam("t") Optional<StatsDMetricType> type,
                      @QueryParam("v") Optional<Integer> value) {
        record(path, type, value);
        return gif;
    }

    private void record(String path, Optional<StatsDMetricType> type, Optional<Integer> value) {
        if (path != null) {
            type.or(StatsDMetricType.COUNTER).record(statsd, path, value);
        }
    }
}
