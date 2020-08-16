package com.github.driftserver.core;

import com.github.driftserver.core.metamodel.DummyModel1;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.Module;
import com.github.driftserver.core.metamodel.StandardCoreModule;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.recording.DummySystemState1;
import com.github.driftserver.core.recording.storage.RecordingStorage;

import java.nio.file.Path;

public class TestSubjects {

    public static ModelStore aModelStore(Module... modules) {

        return ModelStore.builder()
                .withModules(modules)
                .build();
    }

    public static RecordingStorage aRecordingStorage(Path baseDir) {
        ModelStore modelStore = aModelStore(new StandardCoreModule(baseDir));
        return new RecordingStorage(modelStore);
    }


    public static DummyModel1 aDummyModel1() {
        String att1 = "att1";
        String att2 = "att2";
        return new DummyModel1(att1, att2);
    }

    public static DummySystemState1 aSystemState() {
        String att1 = "att1";
        String att2 = "att2";
        return new DummySystemState1(att1, att2);
    }


    public static  ModelURN aParentModelURN() {
        String path1 = "A";
        String path2 = "A";

        return new ModelURN(new ModelId(path1), new ModelId(path2));
    }
}
