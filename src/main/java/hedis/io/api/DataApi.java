package hedis.io.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import redis.clients.jedis.Jedis;

@Path("/")
public class DataApi {
	private static final boolean DEBUG = false;

	@Produces("application/json")
	@POST
	@Path("hedis")
	public Response getHedis(@Context HttpHeaders headers, String query) {
		Jedis jedis = null;

		try {
			jedis = new Jedis("localhost");

			String value = jedis.get(query);

			if (DEBUG) {
				System.out.println("value: " + value);
			}

			return Response.ok(value).build();
		} catch (Exception e) {
			e.printStackTrace();

			return Response.serverError().build();
		} finally {
			jedis.close();
		}
	}

	@Produces("application/json")
	@POST
	@Path("redis")
	public Response getRedis(@Context HttpHeaders headers, String query) {
		Jedis jedis = null;
		Connection conn = null;
		try {
			jedis = new Jedis("localhost");

			String value = jedis.get(query);

			if (value == null || value.isEmpty()) {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost/hedistest?userUnicode=true";
				String username = "root";
				String password = "PASSWORD";

				conn = DriverManager.getConnection(url, username, password);
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(query);

				while (rs.next()) {
					if (DEBUG) {
						System.out.println(String.format("%s\t%s",
								rs.getString("id"), rs.getString("name")));
					} else {
						rs.getString("id");
						rs.getString("name");
					}
				}

				return Response.ok("I found!!!").build();
			} else {
				return Response.ok(value).build();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return Response.serverError().build();
		} finally {
			try {
				conn.close();
				jedis.close();
			} catch (Exception e) {
				e.printStackTrace();

				return Response.serverError().build();
			}
		}
	}
}