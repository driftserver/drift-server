package io.drift.plugin.jdbc;

import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.data.Row;
import io.drift.jdbc.domain.data.TableDelta;
import io.drift.jdbc.domain.data.TableSnapShot;
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

public class DBSnapShotDetailComponent extends Panel {

    public class TableSnapShotFragment extends Fragment {
        public TableSnapShotFragment(String id, TableSnapShot tableSnapShot, TableMetaData tableMetaData) {
            super(id, "tableSnapShotFragment", DBSnapShotDetailComponent.this);
            List<ColumnMetaData> columnsInOrder = tableMetaData.getColumnsInOrder();

            add(new Label("tableName", tableSnapShot.getTable()));
            add(listView("columnsInOrder", columnsInOrder, item -> {
                item.add(new Label("columnName", item.getModelObject().getName()));
            }));
            add(rowsListView("rows", tableSnapShot.getRows(), columnsInOrder));

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


    public DBSnapShotDetailComponent(String id, DBSnapShot dbSnapShot, DBMetaData dbMetaData) {
        super(id);

        List<TableSnapShot> tableSnapShots = dbSnapShot.getTableSnapShots().stream()
                .filter(tableSnapShot -> tableSnapShot.getRows().size() > 0)
                .collect(Collectors.toList());

        add(listView("tableSnapShots", tableSnapShots, item -> {
            TableSnapShot tableSnapShot = item.getModelObject();
            TableMetaData tableMetaData = dbMetaData.getTables().get(tableSnapShot.getTable());
            item.add(new TableSnapShotFragment("tableSnapShot", tableSnapShot, tableMetaData));
        }));

    }
}
