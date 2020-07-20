package io.drift.elasticsearch;

import junit.framework.TestCase;

public class StoreTest extends TestCase {
/*
    private YamlModelSerializer createYamlSerializer() {
        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(new DriftCoreJacksonModule());
        yamlModelSerializer.registerModule(new DriftElasticSearchJacksonModule());
        return yamlModelSerializer;
    }

    private JsonModelSerializer createJsonSerializer() {
        JsonModelSerializer serializer = new JsonModelSerializer();
        serializer.registerModule(new DriftCoreJacksonModule());
        serializer.registerModule(new DriftElasticSearchJacksonModule());
        return serializer;
    }
*/
    /*
    pivate SystemDescription generateMockSystemDescription() {
        DummyFileSystemDescriptionBuilder dummyFileSystemDescriptionBuilder = new DummyFileSystemDescriptionBuilder();
        return dummyFileSystemDescriptionBuilder.createDummy();
    }
    */

    /*
    public void test_system_description_yaml_serialization() throws ModelStoreException {
        YamlModelSerializer yamlModelSerializer = createYamlSerializer();
        SystemDescription fileSystemDescription = generateMockSystemDescription();

        String content = yamlModelSerializer.from(fileSystemDescription);
        System.out.println(content);

        SystemDescription fileSystemDescription2 = (SystemDescription) yamlModelSerializer.loadModel(content, SystemDescription.class);
        String content2 = yamlModelSerializer.from(fileSystemDescription2);

        Assert.assertEquals(content, content2);

    }
    */
/*
    public void test_snapshot_serialization () throws StorableSerializationException {

        DummyElasticSearchSnapshotBuilder snapshotBuilder = new DummyElasticSearchSnapshotBuilder();
        ElasticSearchSnapshot snapshot = snapshotBuilder.snapshot();

        JsonModelSerializer serializer = createJsonSerializer();

        String content = serializer.from(snapshot);
        System.out.println(content);

        ElasticSearchSnapshot snapshot2 =  (ElasticSearchSnapshot) serializer.loadModel(content, ElasticSearchSnapshot.class);
        String content2 = serializer.from(snapshot2);

        Assert.assertEquals(content, content2);

    }
*/
    /*
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
