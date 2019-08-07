package io.drift.plugin.filesystem;

import io.drift.core.WicketComponentFactory;
import io.drift.core.WicketComponentFactoryMethod;
import io.drift.filesystem.FileSystemDelta;
import io.drift.filesystem.FileSystemSettings;
import io.drift.filesystem.FileSystemSnapshot;
import io.drift.ui.app.page.recording.SubSystemStateDetailView;
import io.drift.ui.app.page.recording.SubSystemStateSummaryView;
import io.drift.ui.app.page.recording.SystemInteractionDetailView;
import io.drift.ui.app.page.recording.SystemInteractionSummaryView;
import io.drift.ui.app.page.system.ConnectionDetailsView;
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
    public FileSystemSnapshotSummaryComponent fileSystemSnapshotSummaryComponent(String id, FileSystemSnapshot fileSystemSnapshot, FileSystemSettings fileSystemSettings) {
        return new FileSystemSnapshotSummaryComponent(id, fileSystemSnapshot, fileSystemSettings);
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
