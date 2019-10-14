package io.drift.plugin.elasticsearch.ui;

import io.drift.core.WicketComponentFactory;
import io.drift.core.WicketComponentFactoryMethod;
import io.drift.elasticsearch.ElasticSearchDelta;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.elasticsearch.ElasticSearchSnapshot;
import io.drift.ui.app.page.recording.SubSystemStateDetailView;
import io.drift.ui.app.page.recording.SubSystemStateSummaryView;
import io.drift.ui.app.page.recording.SystemInteractionDetailView;
import io.drift.ui.app.page.recording.SystemInteractionSummaryView;
import io.drift.ui.app.page.system.ConnectionDetailsView;
import org.springframework.stereotype.Component;

@Component
@WicketComponentFactory
public class ElasticSearchWicketComponents {

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchSettings.class,
            viewType = ConnectionDetailsView.class
    )
    public ElasticSearchSettingsComponent ElasticSearchSettings(String id, ElasticSearchSettings settings) {
        return new ElasticSearchSettingsComponent(id, settings);
    }

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchSnapshot.class,
            viewType = SubSystemStateSummaryView.class
    )
    public ElasticSearchSnapshotSummaryComponent ElasticSearchSnapshotSummaryComponent(String id, ElasticSearchSnapshot snapshot) {
        return new ElasticSearchSnapshotSummaryComponent(id, snapshot);
    }

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchSnapshot.class,
            viewType = SubSystemStateDetailView.class
    )
    public ElasticSearchSnapshotDetailComponent ElasticSearchSnapshotDetailComponent(String id, ElasticSearchSnapshot snapshot, ElasticSearchSettings settings) {
        return new ElasticSearchSnapshotDetailComponent(id, snapshot, settings);
    }

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchDelta.class,
            viewType = SystemInteractionSummaryView.class
    )
    public ElasticSearchDeltaSummaryComponent ElasticSearchDeltaSummaryComponent(String id, ElasticSearchDelta delta) {
        return new ElasticSearchDeltaSummaryComponent(id, delta);
    }

    @WicketComponentFactoryMethod(
            dataType = ElasticSearchDelta.class,
            viewType = SystemInteractionDetailView.class
    )
    public ElasticSearchDeltaComponent ElasticSearchDeltaComponent(String id, ElasticSearchDelta delta, ElasticSearchSettings settings) {
        return new ElasticSearchDeltaComponent(id, delta, settings);
    }


}
