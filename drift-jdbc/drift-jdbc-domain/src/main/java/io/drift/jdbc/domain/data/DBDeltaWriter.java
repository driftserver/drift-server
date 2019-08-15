package io.drift.jdbc.domain.data;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import io.drift.jdbc.domain.metadata.ColumnMetaData;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.TableMetaData;

public class DBDeltaWriter {

	private interface Format {
		void delete(PrintStream writer, TableMetaData table, Row row);

		void insert(PrintStream writer, TableMetaData table, Row row);

		void reportFooter(PrintStream writer);

		void reportHeader(PrintStream writer);

		void tableFooter(PrintStream writer, TableMetaData table);

		void tableHeader(PrintStream writer, TableMetaData table);

		void update(PrintStream writer, TableMetaData table, Row row);
	}

	public enum FormatType {
		HTML, TEXT
	}

	private class HTMLFormat implements Format {
		@Override
		public void delete(PrintStream writer, TableMetaData table, Row row) {
			writer.println("\t\t\t\t\t\t<tr>");
			writer.println("\t\t\t\t\t\t<th class=\"danger\" lifecycle=\"row\">D</th>");
			writeRow(writer, table, row);
			writer.println("\t\t\t\t\t\t</tr>");
		}

		@Override
		public void insert(PrintStream writer, TableMetaData table, Row row) {
			writer.println("\t\t\t\t\t\t<tr>");
			writer.println("\t\t\t\t\t\t<th class=\"success\" width=20pt lifecycle=\"row\">I</th>");
			writeRow(writer, table, row);
			writer.println("\t\t\t\t\t\t</tr>");
		}

		@Override
		public void reportFooter(PrintStream writer) {
			writer.println("\t\t\t</div>");
		}

		@Override
		public void reportHeader(PrintStream writer) {
			writer.println("\t\t\t<div>");
		}

		@Override
		public void tableFooter(PrintStream writer, TableMetaData table) {
			writer.println("\t\t\t\t\t</tbody>");
			writer.println("\t\t\t\t</table>");
			writer.println("\t\t\t</div>");
		}

		@Override
		public void tableHeader(PrintStream writer, TableMetaData table) {
			int width = 20 + 150 * table.getColumns().size();

			writer.println("\t\t\t<div>");
			writer.println(table.getName());
			writer.println("\t\t\t\t<table style=\"table-layout: fixed;width: " + width + "px;\" class=\"table table-bordered table-condensed\">");
			writer.println("\t\t\t\t\t<thead>");
			writer.println("\t\t\t\t\t\t<tr>");
			writer.println("\t\t\t\t\t\t\t<th style=\"width:20pt\"></th>");
			for (ColumnMetaData column : table.getColumnsInOrder()) {
				if (!ignoreColumn(column)) {
					writer.print("\t\t\t\t\t\t\t<th style=\"width:150pt\">");
					writer.print("\t\t\t\t\t\t\t\t" + column.getName().trim().toLowerCase());
					writer.println("\t\t\t\t\t\t\t</th>");
				}
			}
			writer.println("\t\t\t\t\t\t</tr>");
			writer.println("\t\t\t\t\t</thead>");
			writer.println("\t\t\t\t\t<tbody>");
		}

		@Override
		public void update(PrintStream writer, TableMetaData table, Row row) {
			writer.println("\t\t\t\t\t\t<tr >");
			writer.println("\t\t\t\t\t\t<th class=\"warning\" width=20pt lifecycle=\"row\">U</th>");
			writeRow(writer, table, row);
			writer.println("\t\t\t\t\t\t</tr>");
		}

		private void writeRow(PrintStream writer, TableMetaData table, Row row) {

			for (ColumnMetaData column : table.getColumnsInOrder()) {
				if (!ignoreColumn(column)) {
					writer.print("\t\t\t\t\t\t\t<td style=\"white-space: nowrap;overflow: hidden;text-overflow: ellipsis\">");
					writer.print(row.getValue(column.getName()));
					writer.println("</td>");
				}
			}
		}
	}

	private class TextFormat implements Format {
		@Override
		public void delete(PrintStream writer, TableMetaData table, Row row) {
			writer.print("D ");
			writeRow(writer, table, row);
			writer.println();
		}

		@Override
		public void insert(PrintStream writer, TableMetaData table, Row row) {
			writer.print("I ");
			writeRow(writer, table, row);
			writer.println();
		}

		@Override
		public void reportFooter(PrintStream writer) {
		}

		@Override
		public void reportHeader(PrintStream writer) {
			// writer.println("=======DB Delta Report ========");
		}

		@Override
		public void tableFooter(PrintStream writer, TableMetaData table) {
		}

		@Override
		public void tableHeader(PrintStream writer, TableMetaData table) {
			writer.println();
			writer.println(table.getName());
			writer.println("-----------------------------------------------------------------".substring(0, table.getName().length()));
		}

		@Override
		public void update(PrintStream writer, TableMetaData table, Row row) {
			writer.print("U ");
			writeRow(writer, table, row);
			writer.println();
		}

		private void writeRow(PrintStream writer, TableMetaData table, Row row) {
			for (ColumnMetaData column : table.getColumnsInOrder()) {
				if (ignoreColumn(column))
					continue;
				writer.print(" | ");
				writer.print(column.getName());
				writer.print(": ");
				writer.print(row.getValue(column.getName()));
			}
		}
	}

	private List<String> columnsToIgnore = Arrays.asList("INSID", "UPDDATE", "UPDID", "INSDATE");
	private Format format;

	public DBDeltaWriter(FormatType formatType) {
		switch (formatType) {
		case TEXT:
			format = new TextFormat();
			break;
		case HTML:
			format = new HTMLFormat();
			break;
		}
	}

	private boolean ignoreColumn(ColumnMetaData column) {
		return columnsToIgnore.contains(column.getName());
	}

	public void writeDBDelta(DBMetaData dbMetaData, DBDelta delta, PrintStream writer) {
		format.reportHeader(writer);
		delta.getTableDeltas().values().stream().sorted(TableDelta.COMPARE_BY_TABLE_NAME).forEach(tableDelta -> writeTableDelta(dbMetaData.get(tableDelta.getTable()), tableDelta, writer));
		format.reportFooter(writer);
	}

	public void writeTableDelta(TableMetaData table, TableDelta delta, PrintStream writer) {
		if (delta.getInserts().size() + delta.getDeletes().size() + delta.getDeletes().size() == 0)
			return;
		format.tableHeader(writer, table);
		for (Row row : delta.getInserts()) {
			format.insert(writer, table, row);
		}
		for (RowDelta rowDelta : delta.getUpdates()) {
			format.update(writer, table, rowDelta.getNewRow());
		}
		for (Row row : delta.getDeletes()) {
			format.delete(writer, table, row);
		}
		format.tableFooter(writer, table);
	}

}
