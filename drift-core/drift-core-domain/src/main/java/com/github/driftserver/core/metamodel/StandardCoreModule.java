package com.github.driftserver.core.metamodel;

import com.github.driftserver.core.infra.jackson.DriftCoreJacksonModule;
import com.github.driftserver.core.metamodel.serialization.JsonModelSerializer;
import com.github.driftserver.core.metamodel.serialization.YamlModelSerializer;
import com.github.driftserver.core.metamodel.urn.FileSystemModelURNResolver;
import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.system.SystemDescription;

import java.nio.file.Path;

public class StandardCoreModule extends Module {

    private Path baseDir;

    public StandardCoreModule(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void register(ModelStore.Builder builder) {


        FileSystemModelURNResolver urnResolver = new FileSystemModelURNResolver(baseDir);

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();
        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();

        DriftCoreJacksonModule driftCoreJacksonModule = new DriftCoreJacksonModule();

        builder
                .withURNResolver(urnResolver)
                .withSerializers(jsonModelSerializer, yamlModelSerializer)
                .withJacksonModule(driftCoreJacksonModule)
                .withModel(Recording.class, ModelFormat.JSON)
                .withModel(SystemDescription.class, ModelFormat.YAML);

    }

}
