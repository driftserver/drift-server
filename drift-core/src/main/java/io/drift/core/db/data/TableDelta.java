package io.drift.core.db.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "table", "inserts", "updates", "deletes" })
public class TableDelta implements Serializable {

	static public final Comparator<TableDelta> COMPARE_BY_TABLE_NAME = new Comparator<TableDelta>() {
		@Override
		public int compare(TableDelta td1, TableDelta td2) {
			return td1.getTable().compareTo(td2.getTable());
		}
	};

	private static final long serialVersionUID = 1L;

	private List<Row> deletes = new ArrayList<>();

	private List<Row> inserts = new ArrayList<>();

	private String table;

	private List<Row> updates = new ArrayList<>();

	protected TableDelta() {

	}

	public TableDelta(String table) {
		this.table = table;
	}

	public void addDelete(Row row) {
		deletes.add(row);
	}

	public void addInsert(Row row) {
		inserts.add(row);
	}

	public void addUpdate(Row row, Row lastRow) {
		updates.add(row);
	}

	public List<Row> getDeletes() {
		return deletes;
	}

	public List<Row> getInserts() {
		return inserts;
	}

	public String getTable() {
		return table;
	}

	public List<Row> getUpdates() {
		return updates;
	}

}
