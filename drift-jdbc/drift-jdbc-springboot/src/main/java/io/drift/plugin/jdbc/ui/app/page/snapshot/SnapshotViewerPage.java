package io.drift.plugin.jdbc.ui.app.page.snapshot;

import io.drift.jdbc.domain.data.Row;
import io.drift.jdbc.domain.metadata.ColumnMetaData;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.SnapshotStore;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.*;
import io.drift.ui.app.page.layout.MainLayout;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.wicketstuff.annotation.mount.MountPath;

import javax.inject.Inject;

import static io.drift.ui.infra.WicketUtil.*;

@MountPath("snapshotviewer")
public class SnapshotViewerPage extends MainLayout {

    @Inject
    private SnapshotStore store;

    class RootsViewFragment extends Fragment {
        RootsViewFragment(String id, RootsViewPart rootsPart) {
            super(id, "rootsViewFragment", SnapshotViewerPage.this);
            add(listView("roots", rootsPart.getRoots(), (item)-> {
                Link rootSelection;
                item.add(rootSelection = ajaxLink("rootSelection", (target) -> {
                      rootsPart.select(item.getModelObject());
                      target.add(mainView);
                }));
                rootSelection.add(label("rootName", item.getModelObject().getName()));
            }));
        }
    }

    class TableViewPartFragment extends Fragment {
        TableViewPartFragment(String id, TableViewPart table) {
            super(id, "tableFragment", SnapshotViewerPage.this);
            add(label("name", table.getName()));

            add(listView("columnsInOrder", table.getColumns(), item -> {
                item.add(new Label("columnName", item.getModelObject().getName()));
            }));
            add(listView("relationHeaders", table.getRelations(), (item)-> {
                item.add(label("relationName", item.getModelObject().getName()));
            }));

            add(listView("rows", table.getRows(), (rowItem) -> {

                Row row = rowItem.getModelObject();

                rowItem.add(listView("columns", table.getColumns(), columnItem -> {
                    ColumnMetaData column = columnItem.getModelObject();
                    String value = row.getValue(column.getName());
                    columnItem.add(new Label("value", value));
                }));

                rowItem.add(listView("relationSelections", table.getRelations(), (item)-> {
                    item.add(ajaxLink("relationSelection", (target) -> {
                        table.selectRelation(item.getModelObject(), row);
                        target.add(mainView);
                    }));
                }));

            }));
        }
    }

    class OneToManyRelationViewPartFragment extends Fragment {
        OneToManyRelationViewPartFragment(String id, OneToManyRelationViewPart viewPart) {
            super(id, "oneToManyRelationViewPartFragment", SnapshotViewerPage.this);
            Link select = ajaxLink("select",(target)-> {
                viewPart.select();
                target.add(mainView);
            });
            select.add(label("description", viewPart.getLabel()));
            add(select);
        }

    }

    class MainViewPartFragment extends Fragment {
        MainViewPartFragment(String id, DBSnapshotMainViewPart model) {
            super(id, "mainViewPartFragment", SnapshotViewerPage.this);
            add(listView("rows", model.getGrid().getRows(), (rowItem)-> {
                rowItem.add(listView("columns", rowItem.getModelObject().getCells(), (cellItem)-> {
                    cellItem.add(createView("cell", cellItem.getModelObject()));
                }));
            }));
        }
    }

    private WebMarkupContainer mainView;

    public SnapshotViewerPage() {
        add(mainView = new MainViewPartFragment("mainViewPart", store.getMainViewPart()));
        mainView.setOutputMarkupId(true);
    }

    private Component createView(String id, ViewPart viewPart) {

        if (viewPart instanceof RootsViewPart) {
            return new RootsViewFragment(id, (RootsViewPart) viewPart);
        } else if (viewPart instanceof TableViewPart) {
            return new TableViewPartFragment(id, (TableViewPart)viewPart);
        } else if (viewPart instanceof OneToManyRelationViewPart) {
            return new OneToManyRelationViewPartFragment(id, (OneToManyRelationViewPart)viewPart);
        }
        return label(id, "[empty cell]");

    }

}
