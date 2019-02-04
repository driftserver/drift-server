package io.drift.core.db.metadata;

import java.io.Serializable;
import java.util.Comparator;

public class ColumnMetaData implements Serializable {

	static public final Comparator<ColumnMetaData> COMPARE_BY_POSITION = new Comparator<ColumnMetaData>() {
		@Override
		public int compare(ColumnMetaData col1, ColumnMetaData col2) {
			return col1.getOrdinalPosition() - col2.getOrdinalPosition();
		}
	};

	private static final long serialVersionUID = 1L;

	private String name;

	private int ordinalPosition;

	private String type;

	protected ColumnMetaData() {
	}

	public ColumnMetaData(String name, String type, int ordinalPosition) {
		this.type = type;
		this.name = name;
		this.ordinalPosition = ordinalPosition;
	}

	public String getName() {
		return name;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public String getType() {
		return type;
	}

}
