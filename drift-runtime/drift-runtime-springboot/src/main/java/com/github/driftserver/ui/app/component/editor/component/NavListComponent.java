package com.github.driftserver.ui.app.component.editor.component;

import com.github.driftserver.ui.app.component.editor.structure.NavListElement;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.*;

public class NavListComponent extends Panel {

    public NavListComponent(String id, NavListElement list) {
        super(id);
        add(listView("links", list.getElements().get(), (item) -> {
            String label = item.getModelObject();
            Link link = ajaxLink("link", list.getLinkAction(label), label);
            link.add(label("label", label));
            item.add(link);
        }));
    }


}
