package com.github.driftserver.jdbc;

import com.github.driftserver.core.WicketComponentFactory;
import com.github.driftserver.core.WicketComponentFactoryMethod;
import com.github.driftserver.jdbc.domain.data.DBDelta;
import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;
import com.github.driftserver.ui.app.page.recording.SubSystemStateDetailView;
import com.github.driftserver.ui.app.page.recording.SubSystemStateSummaryView;
import com.github.driftserver.ui.app.page.recording.SystemInteractionDetailView;
import com.github.driftserver.ui.app.page.recording.SystemInteractionSummaryView;
import com.github.driftserver.ui.app.page.system.ConnectionDetailsView;
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
            viewType = SubSystemStateSummaryView.class
    )
    public DBSnapshotSummaryComponent dbSnapshotSummaryComponent(String id, DBSnapShot dbSnapShot) {
        return new DBSnapshotSummaryComponent(id, dbSnapShot);
    }

    @WicketComponentFactoryMethod(
            dataType = DBSnapShot.class,
            viewType = SubSystemStateDetailView.class
    )
    public DBSnapShotDetailComponent dbSnapshotDetailComponent(String id, DBSnapShot dbSnapShot, DBMetaData dbMetaData) {
        return new DBSnapShotDetailComponent(id, dbSnapShot, dbMetaData);
    }

    @WicketComponentFactoryMethod(
            dataType = JDBCConnectionDetails.class,
            viewType = ConnectionDetailsView.class
    )
    public DBConnectionDetailsComponent JDBCConnectionDetailsComponent(String id, JDBCConnectionDetails jdbcConnectionDetails) {
        return new DBConnectionDetailsComponent(id, jdbcConnectionDetails);
    }



}
