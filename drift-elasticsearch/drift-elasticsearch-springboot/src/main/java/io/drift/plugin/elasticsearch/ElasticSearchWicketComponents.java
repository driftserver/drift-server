package io.drift.plugin.elasticsearch;

import io.drift.core.WicketComponentFactory;
import io.drift.core.WicketComponentFactoryMethod;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.ui.app.page.system.ConnectionDetailsView;
import org.springframework.stereotype.Component;

@Component
@WicketComponentFactory
public class ElasticSearchWicketComponents {

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchSettings.class,
            viewType = ConnectionDetailsView.class
    )
    public ElasticSearchSettingsComponent fileSystemSettings(String id, ElasticSearchSettings settings) {
        return new ElasticSearchSettingsComponent(id, settings);
    }


}
