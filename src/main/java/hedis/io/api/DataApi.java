package hedis.io.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("data")
public class DataApi {
	@Produces("application/json")
	@GET
	public Response get(@Context HttpHeaders headers, String body) {
		return Response.ok().build();
	}
}