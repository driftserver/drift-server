package com.github.driftserver.core.recording;

import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.serialization.JsonModelSerializer;
import com.github.driftserver.core.metamodel.urn.FileSystemModelURNResolver;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(JUnit4.class)
public class RecordingStorageTest extends TestCase{

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private RecordingStorage recordingStorage;

    @Before
    public void setUp() throws IOException {
        Path baseDir = tempFolder.newFolder().toPath();
        System.out.println("baseDir: " + baseDir);

        FileSystemModelURNResolver urnResolver = new FileSystemModelURNResolver(baseDir);

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();

        ModelStore modelStore = ModelStore.builder()
                .withSerializer(jsonModelSerializer)
                // .withModelDescriptor()
                .withURNResolver(urnResolver)
                .build();

    }

    @Test
    public void test_storage_load() {

    }

}
