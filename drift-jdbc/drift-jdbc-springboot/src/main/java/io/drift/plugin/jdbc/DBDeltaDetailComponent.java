package io.drift.plugin.jdbc;

import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.Row;
import io.drift.jdbc.domain.data.TableDelta;
import io.drift.jdbc.domain.metadata.ColumnMetaData;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.TableMetaData;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;
import java.util.stream.Collectors;

import static io.drift.ui.infra.WicketUtil.listView;

public class DBDeltaDetailComponent extends Panel {


    public class TableDeltaFragment extends Fragment {
        public TableDeltaFragment(String id, TableDelta tableDelta, TableMetaData tableMetaData) {
            super(id, "tableDeltaFragment", DBDeltaDetailComponent.this);
            List<ColumnMetaData> columnsInOrder = tableMetaData.getColumnsInOrder();

            add(new Label("tableName", tableDelta.getTable()));
            add(listView("columnsInOrder", columnsInOrder, item -> {
                item.add(new Label("columnName", item.getModelObject().getName()));
            }));
            add(rowsListView("inserts", tableDelta.getInserts(), columnsInOrder));
            add(rowsListView("updates", tableDelta.getUpdates(), columnsInOrder));
            add(rowsListView("deletes", tableDelta.getDeletes(), columnsInOrder));

        }

        private ListView<Row> rowsListView(String id, List<Row> rows, List<ColumnMetaData> columnsInOrder) {
            return listView(id, rows, rowItem -> {
                Row row = rowItem.getModelObject();
                rowItem.add(listView("columns", columnsInOrder, columnItem -> {
                    ColumnMetaData column = columnItem.getModelObject();
                    String value = row.getValue(column.getName());
                    columnItem.add(new Label("value", value));
                }));
            });
        }
    }

    public DBDeltaDetailComponent(String id, DBDelta dbDelta, DBMetaData dbMetaData) {
        super(id);

        List<TableDelta> tableDeltas = dbDelta.getTableDeltas().values().stream()
                .filter(tableDelta -> tableDelta.getInserts().size() + tableDelta.getUpdates().size() + tableDelta.getDeletes().size() > 0).collect(Collectors.toList());

        add(listView("tableDeltas", tableDeltas, item -> {
            TableDelta tableDelta = item.getModelObject();
            TableMetaData tableMetaData = dbMetaData.getTables().get(tableDelta.getTable());
            item.add(new TableDeltaFragment("tableDelta", tableDelta, tableMetaData));
        }));
    }


}
