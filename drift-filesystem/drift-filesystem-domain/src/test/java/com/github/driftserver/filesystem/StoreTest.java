package com.github.driftserver.filesystem;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.StandardCoreModule;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.system.SystemDescription;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(JUnit4.class)
public class StoreTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private ModelStore modelStore;

    @Before
    public void setUp() throws IOException {

        Path baseDir = tempFolder.newFolder().toPath();

        modelStore = ModelStore.builder()
                .withModules(new StandardCoreModule(baseDir), new DriftFileSystemModule())
                // .withModel(DBDelta.class, ModelFormat.JSON)
                .build();
    }

    private SystemDescription generateMockSystemDescription() {
        DummyFileSystemDescriptionBuilder dummyFileSystemDescriptionBuilder = new DummyFileSystemDescriptionBuilder();
        return dummyFileSystemDescriptionBuilder.createDummy();
    }

    @Test
    public void test_system_description_yaml_serialization() throws ModelException {
        SystemDescription systemDescription = generateMockSystemDescription();

        ModelURN id1 = new ModelURN(new ModelId("systemdescription"), new ModelId("v1"));

        modelStore.write(systemDescription, id1);
        SystemDescription systemDescription2 = modelStore.read(id1, SystemDescription.class);

    }

/*
    public void test_snapshot_serialization () throws StorableSerializationException {

        DummyFileSystemSnapshotBuilder snapshotBuilder = new DummyFileSystemSnapshotBuilder();
        FileSystemSnapshot snapshot = snapshotBuilder.fileSystemSnapshot();


    }

    public void test_delta_serialization () throws StorableSerializationException {
        DummyFileSystemDeltaBuilder fileSystemDeltaBuilder = new DummyFileSystemDeltaBuilder();
        FileSystemDelta fileSystemDelta = fileSystemDeltaBuilder.fileSystemDelta();

        JsonModelSerializer serializer = createJsonSerializer();

        String content = serializer.from(fileSystemDelta);
        System.out.println(content);

        FileSystemDelta fileSystemDelta2 =  (FileSystemDelta) serializer.loadModel(content, FileSystemDelta.class);
        String content2 = serializer.from(fileSystemDelta2);

        Assert.assertEquals(content, content2);

    }
*/
}
