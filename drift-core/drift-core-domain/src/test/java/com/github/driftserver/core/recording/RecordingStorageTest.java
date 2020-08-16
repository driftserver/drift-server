package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.recording.storage.RecordingStorage;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static com.github.driftserver.core.TestSubjects.aRecordingStorage;

@RunWith(JUnit4.class)
public class RecordingStorageTest extends TestCase{

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private RecordingStorage recordingStorage;

    @Before
    public void setUp() throws IOException {
        Path baseDir = tempFolder.newFolder().toPath();
        recordingStorage = aRecordingStorage(baseDir);
    }

    @Test
    public void test_storage_load() {
        RecordingId recordingId = new RecordingId(UUID.randomUUID().toString());
        Recording recording = new Recording(recordingId);

        recordingStorage.store(recording);

        Recording recording_after = recordingStorage.load(recordingId);
        assertNotNull(recording_after);
        assertEquals(recordingId, recording_after.getId());

        List<RecordingSummary> summaries = recordingStorage.list();

        assertNotNull(summaries);
        assertEquals(1, summaries.size());
        RecordingSummary recordingSummary = summaries.get(0);
        assertEquals(recordingId, recordingSummary.getRecordingId());
        assertNotNull(recordingSummary.getTimeStamp());

    }

}
