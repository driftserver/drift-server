package io.drift.jdbc.domain.data;

import java.io.Serializable;

public class DeltaSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	public int deletes;

	public int inserts;

	@Override
	public String toString() {
		return "DeltaSummary [inserts=" + inserts + ", updates=" + updates + ", deletes=" + deletes + "]";
	}

	public int updates;

}
