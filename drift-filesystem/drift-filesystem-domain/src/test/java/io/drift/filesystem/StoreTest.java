package io.drift.filesystem;

import io.drift.core.infra.DriftCoreJacksonModule;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.serialization.StorableSerializationException;
import io.drift.core.store.serialization.YamlModelSerializer;
import io.drift.core.system.SystemDescription;
import junit.framework.TestCase;
import org.junit.Assert;

public class StoreTest extends TestCase {

    private YamlModelSerializer createYamlSerializer() {
        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(new DriftCoreJacksonModule());
        yamlModelSerializer.registerModule(new DriftFileSystemJacksonModule());
        return yamlModelSerializer;
    }

    private JsonModelSerializer createJsonSerializer() {
        JsonModelSerializer serializer = new JsonModelSerializer();
        serializer.registerModule(new DriftCoreJacksonModule());
        serializer.registerModule(new DriftFileSystemJacksonModule());
        return serializer;
    }

    private SystemDescription generateMockSystemDescription() {
        DummyFileSystemDescriptionBuilder dummyFileSystemDescriptionBuilder = new DummyFileSystemDescriptionBuilder();
        return dummyFileSystemDescriptionBuilder.createDummy();
    }

    public void test_system_description_yaml_serialization() throws ModelStoreException {
        YamlModelSerializer yamlModelSerializer = createYamlSerializer();
        SystemDescription fileSystemDescription = generateMockSystemDescription();

        String content = yamlModelSerializer.from(fileSystemDescription);
        System.out.println(content);

        SystemDescription fileSystemDescription2 = (SystemDescription) yamlModelSerializer.loadModel(content, SystemDescription.class);
        String content2 = yamlModelSerializer.from(fileSystemDescription2);

        Assert.assertEquals(content, content2);

    }

    public void test_snapshot_serialization () throws StorableSerializationException {

        DummyFileSystemSnapshotBuilder snapshotBuilder = new DummyFileSystemSnapshotBuilder();
        FileSystemSnapshot snapshot = snapshotBuilder.fileSystemSnapshot();

        JsonModelSerializer serializer = createJsonSerializer();

        String content = serializer.from(snapshot);
        System.out.println(content);

        FileSystemSnapshot snapshot2 =  (FileSystemSnapshot) serializer.loadModel(content, FileSystemSnapshot.class);
        String content2 = serializer.from(snapshot2);

        Assert.assertEquals(content, content2);


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

}
