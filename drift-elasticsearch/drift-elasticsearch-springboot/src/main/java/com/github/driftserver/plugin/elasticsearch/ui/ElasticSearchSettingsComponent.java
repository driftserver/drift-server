package com.github.driftserver.plugin.elasticsearch.ui;

import com.github.driftserver.elasticsearch.ElasticSearchSettings;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;
import static com.github.driftserver.ui.infra.WicketUtil.listView;

public class ElasticSearchSettingsComponent extends Panel {

    public ElasticSearchSettingsComponent(String id, ElasticSearchSettings settings) {
        super(id);
        add(label("url", settings.getUrl()));
        add(label("userName", settings.getUserName()));
        add(label("password", settings.getPassword()));
        add(listView("indices", () -> settings.getIndices(), listItem -> {
            listItem.add(label("index", listItem.getModelObject()));
        }));
    }
}
