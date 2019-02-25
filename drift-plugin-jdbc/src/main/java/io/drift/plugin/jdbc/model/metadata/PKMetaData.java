package io.drift.plugin.jdbc.model.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PKMetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ColumnMetaData> columns = new ArrayList<>();

	public void addColumn(ColumnMetaData column) {
		columns.add(column);
	}

	public List<ColumnMetaData> getColumns() {
		return columns;
	}
}
