package io.drift.core.store;

import java.util.UUID;

public class IDGeneratorUUIDImpl implements IDGenerator {

	@Override
	public String createId() {
		return UUID.randomUUID().toString();
	}

}
