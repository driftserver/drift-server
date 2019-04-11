package io.drift.core.store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IDGeneratorTimestampImpl implements IDGenerator {

	@Override
	public String createId() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm_ss");
		return formatter.format(now);
	}

}
