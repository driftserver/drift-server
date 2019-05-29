package io.drift.plugin.jdbc;

import io.drift.core.WicketComponentFactory;
import io.drift.core.WicketComponentFactoryMethod;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.ui.app.page.recording.SubSystemStateDetailView;
import io.drift.ui.app.page.recording.SystemInteractionDetailView;
import io.drift.ui.app.page.recording.SystemInteractionSummaryView;
import org.springframework.stereotype.Component;

@Component
@WicketComponentFactory
public class DatabaseWicketComponents {

    @WicketComponentFactoryMethod(
            dataType = DBDelta.class,
            viewType = SystemInteractionSummaryView.class
    )
    public DBDeltaSummaryComponent dbDeltaSummaryView(String id, DBDelta dbDelta) {
        return new DBDeltaSummaryComponent(id, dbDelta.getSubSystem(), dbDelta.getSummary().inserts, dbDelta.getSummary().updates, dbDelta.getSummary().deletes);
    }

    @WicketComponentFactoryMethod(
            dataType = DBDelta.class,
            viewType = SystemInteractionDetailView.class
    )
    public DBDeltaDetailComponent dbDeltaDetailView(String id, DBDelta dbDelta, DBMetaData dbMetaData) {
        return new DBDeltaDetailComponent(id, dbDelta, dbMetaData);
    }

    @WicketComponentFactoryMethod(
            dataType = DBSnapShot.class,
            viewType = SubSystemStateDetailView.class
    )
    public DBSnapShotDetailComponent dbSnapshotDetailComponent(String id, DBSnapShot dbSnapShot, DBMetaData dbMetaData) {
        return new DBSnapShotDetailComponent(id, dbSnapShot, dbMetaData);
    }


}
