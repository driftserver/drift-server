package io.drift.jdbc.domain.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Row implements Serializable {

	static Object Null = new Object();

	private static final long serialVersionUID = 1L;

	private Map<String, String> values = new HashMap<>();

	public void addValue(String column, String value) {
		values.put(column, value);
	}

	public String getValue(String column) {
		return values.get(column);
	}

}
