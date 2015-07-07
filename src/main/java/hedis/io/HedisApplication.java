package hedis.io;

import hedis.io.api.DataApi;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class HedisApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> restServiceSet = new HashSet<Class<?>>();

		restServiceSet.add(DataApi.class);

		return restServiceSet;
	}
}