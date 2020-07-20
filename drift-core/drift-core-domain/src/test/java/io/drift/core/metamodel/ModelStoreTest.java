package io.drift.core.metamodel;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.FileSystemModelURNResolver;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.metamodel.serialization.JsonModelSerializer;
import junit.framework.TestCase;

import java.nio.file.Files;
import java.nio.file.Path;

public class ModelStoreTest extends TestCase {



    private Path baseDir;

    private ModelStore modelStore;

    protected void setUp() throws Exception {
        baseDir = Files.createTempDirectory("files");
        System.out.println("baseDir: " + baseDir);

        FileSystemModelURNResolver urnResolver = new FileSystemModelURNResolver(baseDir);

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();

        modelStore = ModelStore.builder()
                .withSerializer(jsonModelSerializer)
                .withModelDescriptor(new DummyModel1Descriptor())
                .withURNResolver(urnResolver)
                .build();
    }

    public void test_write_then_read() throws ModelException{
        ModelURN urn = new ModelURN(new ModelId("a"), new ModelId("id1"));

        DummyModel1 before = createDummlyModel1();
        modelStore.write(before, urn);

        DummyModel1 after = modelStore.read(urn, DummyModel1.class);

        System.out.println(after);


    }

    private DummyModel1 createDummlyModel1() {
        String att1 = "att1";
        String att2 = "att2";
        DummyModel1 model = new DummyModel1(att1, att2);
        return model;
    }


}
