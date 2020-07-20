package com.github.driftserver.jdbc.domain.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableSnapShot implements Serializable {

	private List<Row> rows = new ArrayList<>();

	private String table;

	public TableSnapShot() {
	}

	public TableSnapShot(String table) {
		this.table = table;
	}

	public void addRow(Row row) {
		rows.add(row);
	}

	public List<Row> getRows() {
		return rows;
	}

	public String getTable() {
		return table;
	}
}
