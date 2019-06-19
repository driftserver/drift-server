package io.drift.plugin.jdbc.ui.app.page.snapshot;

import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.RootsViewPart;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.DBSnapshotMainViewPart;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.TableViewPart;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.SnapshotStore;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.ViewPart;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.infra.WicketUtil;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
        public RootsViewFragment(String id, RootsViewPart rootsPart) {
            super(id, "rootsViewFragment", SnapshotViewerPage.this);
            add(listView("roots", rootsPart.getRoots(), (item)-> {
                Link rootSelection;
                item.add(rootSelection = ajaxLink("rootSelection", (target) -> {
                      rootsPart.select(item.getModelObject());
                      target.add(mainView);
                }));
                rootSelection.add(label("rootName", item.getModelObject().getName()));
            }));
            /*
            add(listView("childParts", rootsPart.getChildren(), (item)->{
                item.add(createView("childPart", item.getModelObject()));
            }));
             */

        }
    }

    class SVTableFragment extends Fragment {
        public SVTableFragment(String id, TableViewPart table) {
            super(id, "tableFragment", SnapshotViewerPage.this);
            add(label("name", table.getName()));
            add(listView("relationHeaders", table.getRelations(), (item)-> {
                item.add(label("relationName", item.getModelObject().getName()));
            }));
            add(listView("relationSelections", table.getRelations(), (item)-> {
                item.add(ajaxLink("relationSelection", (target) -> {
                    table.selectRelation(item.getModelObject().getName());
                    target.add(mainView);
                }));
            }));
        }
    }

    class SVModelViewFragment extends Fragment {
        public SVModelViewFragment(String id, DBSnapshotMainViewPart model) {
            super(id, "modelFragment", SnapshotViewerPage.this);
            add(WicketUtil.listView("tables", model.getTables(), (item)-> {
                item.add(new SVTableFragment("table", item.getModelObject()));
            }));
        }
    }

    private WebMarkupContainer mainView;

    public SnapshotViewerPage() {
        add(mainView = createView("viewPart", store.getViewPart()));
        mainView.setOutputMarkupId(true);
    }

    private WebMarkupContainer createView(String id, ViewPart viewPart) {

        if (viewPart instanceof RootsViewPart) {
            return new RootsViewFragment(id, (RootsViewPart) viewPart);
        } else if (viewPart instanceof TableViewPart) {
            return new SVTableFragment(id, (TableViewPart)viewPart);
        }
        return null; // label(id, "no view for " + viewPart.getClass().getName());

    }

}
