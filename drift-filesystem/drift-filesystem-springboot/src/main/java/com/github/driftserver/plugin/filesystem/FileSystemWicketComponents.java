package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.core.WicketComponentFactory;
import com.github.driftserver.core.WicketComponentFactoryMethod;
import com.github.driftserver.filesystem.FileSystemDelta;
import com.github.driftserver.filesystem.FileSystemSettings;
import com.github.driftserver.filesystem.FileSystemSnapshot;
import com.github.driftserver.ui.app.page.recording.SubSystemStateDetailView;
import com.github.driftserver.ui.app.page.recording.SubSystemStateSummaryView;
import com.github.driftserver.ui.app.page.recording.SystemInteractionDetailView;
import com.github.driftserver.ui.app.page.recording.SystemInteractionSummaryView;
import com.github.driftserver.ui.app.page.system.ConnectionDetailsView;
import org.springframework.stereotype.Component;

@Component
@WicketComponentFactory
public class FileSystemWicketComponents {

    @WicketComponentFactoryMethod(
            dataType = FileSystemSettings.class,
            viewType = ConnectionDetailsView.class
    )
    public FileSystemSettingsComponent fileSystemSettings(String id, FileSystemSettings fileSystemSettings) {
        return new FileSystemSettingsComponent(id, fileSystemSettings);
    }

    @WicketComponentFactoryMethod(
            dataType = FileSystemSnapshot.class,
            viewType = SubSystemStateSummaryView.class
    )
    public FileSystemSnapshotSummaryComponent fileSystemSnapshotSummaryComponent(String id, FileSystemSnapshot fileSystemSnapshot) {
        return new FileSystemSnapshotSummaryComponent(id, fileSystemSnapshot);
    }

    @WicketComponentFactoryMethod(
            dataType = FileSystemSnapshot.class,
            viewType = SubSystemStateDetailView.class
    )
    public FileSystemSnapshotDetailComponent fileSystemSnapshotDetailComponent(String id, FileSystemSnapshot fileSystemSnapshot, FileSystemSettings fileSystemSettings) {
        return new FileSystemSnapshotDetailComponent(id, fileSystemSnapshot, fileSystemSettings);
    }

    @WicketComponentFactoryMethod(
            dataType = FileSystemDelta.class,
            viewType = SystemInteractionSummaryView.class
    )
    public FileSystemDeltaSummaryComponent fileSystemDeltaSummaryComponent(String id, FileSystemDelta fileSystemDelta) {
        return new FileSystemDeltaSummaryComponent(id, fileSystemDelta);
    }

    @WicketComponentFactoryMethod(
            dataType = FileSystemDelta.class,
            viewType = SystemInteractionDetailView.class
    )
    public FileSystemDeltaComponent fileSystemDeltaComponent(String id, FileSystemDelta fileSystemDelta, FileSystemSettings fileSystemSettings) {
        return new FileSystemDeltaComponent(id, fileSystemDelta, fileSystemSettings);
    }

}
