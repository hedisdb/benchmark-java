package hedis.io;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		initialServer();

		while (true) {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				LOG.error("Exception: ", e);

				throw new RuntimeException();
			}
		}
	}

	private static void initialServer() {
		URI uri = createUri();

		HedisApplication ap = new HedisApplication();
		ResourceConfig conf = ResourceConfig.forApplication(ap);

		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(uri,
				conf, false);

		try {
			httpServer.start();
		} catch (IOException e) {
			LOG.error("Exception: ", e);

			throw new RuntimeException();
		}
	}

	private static URI createUri() {
		StringBuffer sb = new StringBuffer("http://localhost:")
				.append(VMProperties.getHttpPort());

		try {
			return new URI(sb.toString());
		} catch (Exception e) {
			LOG.error("Exception: ", e);

			throw new RuntimeException();
		}
	}
}