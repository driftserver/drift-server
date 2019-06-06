package com.acme;

import com.fasterxml.jackson.databind.Module;
import io.drift.core.store.ModelStore;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.serialization.YamlModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.core.store.storage.LuceneMetaDataStorage;
import io.drift.core.system.SystemDescription;
import io.drift.jdbc.infra.DriftJDBCJacksonModule;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.nio.file.Paths;

import static io.drift.core.store.serialization.JsonModelSerializer.JSON_FORMAT;
import static io.drift.core.store.serialization.YamlModelSerializer.YAML_FORMAT;

public class AcmeModelStoreFactory implements FactoryBean<ModelStore>, DisposableBean {

    private LuceneMetaDataStorage luceneMetaDataStorage;

    @Override
    public ModelStore getObject() throws Exception {

        Module jdbcJacksonModule = new DriftJDBCJacksonModule();

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();
        jsonModelSerializer.registerJacksonModule(jdbcJacksonModule);

        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(jdbcJacksonModule);

        return new ModelStore()

                .withSerializer(jsonModelSerializer)
                .withSerializer(yamlModelSerializer)

                .withDefaultFormat(JSON_FORMAT)
                .withFormatForClass(SystemDescription.class, YAML_FORMAT)

                .withModelStorage(new FileSystemModelStorage(Paths.get("store")))

                .withMetaDataStorage(luceneMetaDataStorage = new LuceneMetaDataStorage());

    }

    @Override
    public Class<?> getObjectType() {
        return ModelStore.class;
    }

    @Override
    public void destroy() {
        if (luceneMetaDataStorage != null) luceneMetaDataStorage.shutDown();
    }

}
