package com.acme;

import com.fasterxml.jackson.databind.Module;
import io.drift.core.infra.jackson.DriftCoreJacksonModule;
import io.drift.core.metamodel.ModelStore;
import io.drift.core.metamodel.serialization.JsonModelSerializer;
import io.drift.core.metamodel.serialization.YamlModelSerializer;
import io.drift.elasticsearch.DriftElasticSearchJacksonModule;
import io.drift.filesystem.DriftFileSystemJacksonModule;
import io.drift.jdbc.infra.DriftJDBCJacksonModule;
import org.springframework.beans.factory.FactoryBean;

public class AcmeModelStoreFactory implements FactoryBean<ModelStore> {

    @Override
    public ModelStore getObject() {

        Module coreJacksonModule = new DriftCoreJacksonModule();
        Module jdbcJacksonModule = new DriftJDBCJacksonModule();
        Module fileSystemJacksonModule = new DriftFileSystemJacksonModule();
        Module esModule = new DriftElasticSearchJacksonModule();

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();
        jsonModelSerializer.registerModule(coreJacksonModule);
        jsonModelSerializer.registerModule(jdbcJacksonModule);
        jsonModelSerializer.registerModule(fileSystemJacksonModule);
        jsonModelSerializer.registerModule(esModule);

        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(coreJacksonModule);
        yamlModelSerializer.registerModule(jdbcJacksonModule);
        yamlModelSerializer.registerModule(fileSystemJacksonModule);
        yamlModelSerializer.registerModule(esModule);

        return null;

    }

    @Override
    public Class<?> getObjectType() {
        return ModelStore.class;
    }


}
