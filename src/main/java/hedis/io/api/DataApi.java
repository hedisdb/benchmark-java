package hedis.io.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import redis.clients.jedis.Jedis;

@Path("data")
public class DataApi {
	@Produces("application/json")
	@GET
	public Response get(@Context HttpHeaders headers, String body) {
		Jedis jedis = new Jedis("localhost");

		String value = jedis.get(headers.getHeaderString("hedis"));

		System.out.println("value: " + value);

		jedis.close();

		return Response.ok().build();
	}
}