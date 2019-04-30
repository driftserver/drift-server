package io.drift.ui.app.page.layout;

import io.drift.ui.app.page.recorder.RecorderPage;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.ajaxLink;

public class Menu extends Panel {

    public Menu(String id) {
        super(id);
        add(ajaxLink("home", (target) -> setResponsePage(getApplication().getHomePage())));
        add(ajaxLink("recorder", (target) -> setResponsePage(RecorderPage.class)));

    }

}
