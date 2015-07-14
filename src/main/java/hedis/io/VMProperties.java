package hedis.io;

import java.util.Properties;

public class VMProperties {
	private static final Properties SERVER_PROPS = System.getProperties();

	public static int getHttpPort() {
		String strHttpPort = SERVER_PROPS.getProperty("http.port");
		int httpPort = 8090;

		if (strHttpPort == null) {
			return httpPort;
		}

		try {
			httpPort = Integer.valueOf(strHttpPort);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpPort;
	}

	public static String getHedisHost() {
		String hedisHost = SERVER_PROPS.getProperty("hedis.host");

		if (hedisHost == null || hedisHost.isEmpty()) {
			return "localhost";
		}

		return hedisHost;
	}

	public static String getMySQLHost() {
		String mysqlHost = SERVER_PROPS.getProperty("mysql.host");

		if (mysqlHost == null || mysqlHost.isEmpty()) {
			return "localhost";
		}

		return mysqlHost;
	}

	public static String getMySQLDatabase() {
		String mysqlDatabase = SERVER_PROPS.getProperty("mysql.database");

		if (mysqlDatabase == null || mysqlDatabase.isEmpty()) {
			return "test";
		}

		return mysqlDatabase;
	}

	public static String getMySQLUsername() {
		String mysqlUsername = SERVER_PROPS.getProperty("mysql.username");

		if (mysqlUsername == null || mysqlUsername.isEmpty()) {
			return "root";
		}

		return mysqlUsername;
	}

	public static String getMySQLPassword() {
		String mysqlPassword = SERVER_PROPS.getProperty("mysql.password");

		if (mysqlPassword == null || mysqlPassword.isEmpty()) {
			return "";
		}

		return mysqlPassword;
	}

	public static String getHBaseZookeeper() {
		String hbaseZookeeper = SERVER_PROPS.getProperty("hbase.zookeeper");

		if (hbaseZookeeper == null || hbaseZookeeper.isEmpty()) {
			return "";
		}

		return hbaseZookeeper;
	}
}