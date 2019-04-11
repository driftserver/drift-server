package io.drift.core.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class DriftSessionScope implements Scope {

	public static final String DRIFT_SESSION_SCOPE = "driftsession";

	static private Logger logger = LoggerFactory.getLogger(DriftSessionScope.class);

	private Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<String, Runnable>());

	private Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<String, Object>());

	public DriftSessionScope() {
		logger.info("DriftSessionScope()");
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		System.out.println("get!!!");
		logger.info(String.format("get %s", name));
		if (!scopedObjects.containsKey(name)) {
			scopedObjects.put(name, objectFactory.getObject());
		}
		return scopedObjects.get(name);
	}

	@Override
	public String getConversationId() {
		logger.info("getConversationId");
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		destructionCallbacks.put(name, callback);
	}

	@Override
	public Object remove(String name) {
		destructionCallbacks.remove(name);
		return scopedObjects.remove(name);
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

}
