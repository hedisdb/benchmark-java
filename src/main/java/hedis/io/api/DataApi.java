package hedis.io.api;

import hedis.io.VMProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.NavigableMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

@Path("/")
public class DataApi {
	private static final Logger LOG = LoggerFactory.getLogger(DataApi.class);
	private static final boolean DEBUG = false;

	@Produces("application/json")
	@POST
	@Path("hedis")
	public Response getHedis(@Context HttpHeaders headers, String query) {
		Jedis jedis = null;

		try {
			jedis = new Jedis(VMProperties.getHedisHost());

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
	@Path("redis/mysql")
	public Response getRedisMySQL(@Context HttpHeaders headers, String query) {
		Jedis jedis = null;
		Connection conn = null;

		try {
			jedis = new Jedis(VMProperties.getHedisHost());

			String value = jedis.get(query);

			if (value == null || value.isEmpty()) {
				Class.forName("com.mysql.jdbc.Driver");

				String url = "jdbc:mysql://" + VMProperties.getMySQLHost()
						+ "/" + VMProperties.getMySQLDatabase()
						+ "?userUnicode=true";
				String username = VMProperties.getMySQLUsername();
				String password = VMProperties.getMySQLPassword();

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
				if (conn != null) {
					conn.close();
				}

				if (jedis != null) {
					jedis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

				return Response.serverError().build();
			}
		}
	}

	@Produces("application/json")
	@POST
	@Path("redis/hbase")
	public Response getRedisHBase(@Context HttpHeaders headers, String query) {
		Jedis jedis = null;
		Connection conn = null;
		HTable table = null;

		try {
			jedis = new Jedis(VMProperties.getHedisHost());

			String value = jedis.get(query);

			if (value == null || value.isEmpty()) {
				String[] values = query.split("@");

				Configuration conf = HBaseConfiguration.create();

				table = new HTable(conf, values[0]);

				Get get = new Get(Bytes.toBytes(values[1]));

				Result result = table.get(get);

				StringBuffer sb = new StringBuffer();

				for (Entry<byte[], NavigableMap<byte[], byte[]>> entry : result
						.getNoVersionMap().entrySet()) {
					sb.append(Bytes.toString(entry.getKey()));

					for (Entry<byte[], byte[]> entry2 : entry.getValue()
							.entrySet()) {
						sb.append(Bytes.toString(entry2.getKey())).append(
								Bytes.toString(entry2.getValue()));
					}
				}

				return Response.ok(sb.toString()).build();
			} else {
				return Response.ok(value).build();
			}
		} catch (Exception e) {
			LOG.error("Exception: ", e);

			return Response.serverError().build();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}

				if (jedis != null) {
					jedis.close();
				}

				if (table != null) {
					table.close();
				}
			} catch (Exception e) {
				LOG.error("Exception: ", e);

				return Response.serverError().build();
			}
		}
	}
}