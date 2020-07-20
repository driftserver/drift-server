package com.github.driftserver.jdbc.domain.data;

public class SnapshotConfig {
	
	private String[] tableNames;

	public String[] getTableNames() {
		return tableNames;
	}

	public SnapshotConfig(String[] tableNames) {
		this.tableNames = tableNames;
	}
	

}
